package team.gif.lib;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team.gif.robot.Globals;

import static team.gif.lib.GIFMath.applyDeadband;
import static team.gif.lib.GIFMath.limit;

/**
 * A class for driving differential drive/skid-steer drive platforms such as the Kit of Parts drive
 * base, "tank drive", or West Coast Drive.
 *
 * <p>These drive bases typically have drop-center / skid-steer with two or more wheels per side
 * (e.g., 6WD or 8WD). This class takes a TalonSRX per side. For systems with more than two motors,
 * designate master and follower motors and pass the masters to this class.
 *
 * <p>Four motor drivetrain:
 * <pre><code>
 * public class Robot {
 *   TalonSRX leftMaster = new TalonSRX(0);
 *   TalonSRX leftFollower = new TalonSRX(1);
 *   leftFollower.follow(leftMaster);
 *
 *   TalonSRX rightMaster = new TalonSRX(2);
 *   TalonSRX rightFollower = new TalonSRX(3);
 *   rightFollower.follow(rightMaster);
 *
 *   GIFDrive drive = new GIFDrive(leftMaster, rightMaster);
 * }
 * </code></pre>
 *
 * <p>A differential drive robot has left and right wheels separated by an arbitrary width.
 *
 * <p>Drive base diagram:
 * <pre>
 * |_______|
 * | |   | |
 *   |   |
 * |_|___|_|
 * |       |
 * </pre>
 *
 * <p>Each drive() function provides different inverse kinematic relations for a differential drive
 * robot. Motor outputs for the right side are negated, so motor direction inversion by the user is
 * usually unnecessary.
 *
 * <p>The positive X axis points ahead, the positive Y axis points right, and the positive Z axis
 * points down. Rotations follow the right-hand rule, so clockwise rotation around the Z axis is
 * positive.
 *
 * <p>Inputs smaller then {@value team.gif.robot.Globals#DEFAULT_DEADBAND} will
 * be set to 0, and larger values will be scaled so that the full range is still used. This
 * deadband value can be changed with {@link #setDeadband}.
 *
 * <p>RobotDrive porting guide:
 * <br>{@link #tankDrive(double, double)} is equivalent to
 * {@link edu.wpi.first.wpilibj.RobotDrive#tankDrive(double, double)} if a deadband of 0 is used.
 * <br>{@link #arcadeDrive(double, double)} is equivalent to
 * {@link edu.wpi.first.wpilibj.RobotDrive#arcadeDrive(double, double)} if a deadband of 0 is used
 * and the the rotation input is inverted eg arcadeDrive(y, -rotation)
 * <br>{@link #curvatureDrive(double, double, boolean)} is similar in concept to
 * {@link edu.wpi.first.wpilibj.RobotDrive#drive(double, double)} with the addition of a quick turn
 * mode. However, it is not designed to give exactly the same response.
 */
public class GIFDrive {

    private TalonSRX leftMotor;
    private TalonSRX rightMotor;

    private double quickStopThreshold = Globals.DEFAULT_QUICK_STOP_THRESHOLD;
    private double quickStopAlpha = Globals.DEFAULT_QUICK_STOP_ALPHA;
    private double quickStopAccumulator = 0.0;
    private double deadband = Globals.DEFAULT_DEADBAND;
    private double maxOutput = Globals.MAX_OUTPUT;

    /**
     * Construct a GIFDrive.
     */
    public GIFDrive(TalonSRX leftMotor, TalonSRX rightMotor) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
    }

    /**
     * Arcade drive method for differential drive platform.
     * The calculated values will be squared to decrease sensitivity at low speeds.
     *
     * @param speed    The robot's speed along the X axis [-1.0..1.0]. Forward is positive.
     * @param rotation The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is
     *                  positive.
     */
    public void arcadeDrive(double speed, double rotation) {
        arcadeDrive(speed, rotation, true);
    }

    /**
     * Arcade drive method for differential drive platform.
     *
     * @param speed        The robot's speed along the X axis [-1.0..1.0]. Forward is positive.
     * @param rotation     The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is
     *                      positive.
     * @param squaredInputs If set, decreases the input sensitivity at low speeds.
     */
    public void arcadeDrive(double speed, double rotation, boolean squaredInputs) {
        speed = limit(speed);
        speed = applyDeadband(speed, deadband);

        rotation = limit(rotation);
        rotation = applyDeadband(rotation, deadband);

        // Square the inputs (while preserving the sign) to increase fine control
        // while permitting full power.
        if (squaredInputs) {
            speed = Math.copySign(speed * speed, speed);
            rotation = Math.copySign(rotation * rotation, rotation);
        }

        double leftMotorOutput;
        double rightMotorOutput;

        double maxInput = Math.copySign(Math.max(Math.abs(speed), Math.abs(rotation)), speed);

        if (speed >= 0.0) {
            // First quadrant, else second quadrant
            if (rotation >= 0.0) {
                leftMotorOutput = maxInput;
                rightMotorOutput = speed - rotation;
            } else {
                leftMotorOutput = speed + rotation;
                rightMotorOutput = maxInput;
            }
        } else {
            // Third quadrant, else fourth quadrant
            if (rotation >= 0.0) {
                leftMotorOutput = speed + rotation;
                rightMotorOutput = maxInput;
            } else {
                leftMotorOutput = maxInput;
                rightMotorOutput = speed - rotation;
            }
        }

        leftMotor.set(ControlMode.PercentOutput, limit(leftMotorOutput) * maxOutput);
        rightMotor.set(ControlMode.PercentOutput, -limit(rightMotorOutput) * maxOutput);
    }

    /**
     * Curvature drive method for differential drive platform.
     *
     * <p>The rotation argument controls the curvature of the robot's path rather than its rate of
     * heading change. This makes the robot more controllable at high speeds. Also handles the
     * robot's quick turn functionality - "quick turn" overrides constant-curvature turning for
     * turn-in-place maneuvers.
     *
     * @param speed      The robot's speed along the X axis [-1.0..1.0]. Forward is positive.
     * @param rotation   The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is
     *                    positive.
     * @param isQuickTurn If set, overrides constant-curvature turning for
     *                    turn-in-place maneuvers.
     */
    public void curvatureDrive(double speed, double rotation, boolean isQuickTurn) {
        speed = limit(speed);
        speed = applyDeadband(speed, deadband);

        rotation = limit(rotation);
        rotation = applyDeadband(rotation, deadband);

        double angularPower;
        boolean overPower;

        if (isQuickTurn) {
            if (Math.abs(speed) < quickStopThreshold) {
                quickStopAccumulator = (1 - quickStopAlpha) * quickStopAccumulator
                        + quickStopAlpha * limit(rotation) * 2;
            }
            overPower = true;
            angularPower = rotation * 0.75;
        } else {
            overPower = false;
            angularPower = Math.abs(speed) * rotation - quickStopAccumulator;

            if (quickStopAccumulator > 1) {
                quickStopAccumulator -= 1;
            } else if (quickStopAccumulator < -1) {
                quickStopAccumulator += 1;
            } else {
                quickStopAccumulator = 0.0;
            }
        }

        double leftMotorOutput = speed + angularPower;
        double rightMotorOutput = speed - angularPower;

        // If rotation is overpowered, reduce both outputs to within acceptable range
        if (overPower) {
            if (leftMotorOutput > 1.0) {
                rightMotorOutput -= leftMotorOutput - 1.0;
                leftMotorOutput = 1.0;
            } else if (rightMotorOutput > 1.0) {
                leftMotorOutput -= rightMotorOutput - 1.0;
                rightMotorOutput = 1.0;
            } else if (leftMotorOutput < -1.0) {
                rightMotorOutput -= leftMotorOutput + 1.0;
                leftMotorOutput = -1.0;
            } else if (rightMotorOutput < -1.0) {
                leftMotorOutput -= rightMotorOutput + 1.0;
                rightMotorOutput = -1.0;
            }
        }

        leftMotor.set(ControlMode.PercentOutput, leftMotorOutput * maxOutput);
        rightMotor.set(ControlMode.PercentOutput, -rightMotorOutput * maxOutput);
    }

    /**
     * Tank drive method for differential drive platform.
     * The calculated values will be squared to decrease sensitivity at low speeds.
     *
     * @param leftSpeed  The robot's left side speed along the X axis [-1.0..1.0]. Forward is
     *                   positive.
     * @param rightSpeed The robot's right side speed along the X axis [-1.0..1.0]. Forward is
     *                   positive.
     */
    public void tankDrive(double leftSpeed, double rightSpeed) {
        tankDrive(leftSpeed, rightSpeed, true);
    }

    /**
     * Tank drive method for differential drive platform.
     *
     * @param leftSpeed     The robot left side's speed along the X axis [-1.0..1.0]. Forward is
     *                      positive.
     * @param rightSpeed    The robot right side's speed along the X axis [-1.0..1.0]. Forward is
     *                      positive.
     * @param squaredInputs If set, decreases the input sensitivity at low speeds.
     */
    public void tankDrive(double leftSpeed, double rightSpeed, boolean squaredInputs) {
        leftSpeed = limit(leftSpeed);
        leftSpeed = applyDeadband(leftSpeed, deadband);

        rightSpeed = limit(rightSpeed);
        rightSpeed = applyDeadband(rightSpeed, deadband);

        // Square the inputs (while preserving the sign) to increase fine control
        // while permitting full power.
        if (squaredInputs) {
            leftSpeed = Math.copySign(leftSpeed * leftSpeed, leftSpeed);
            rightSpeed = Math.copySign(rightSpeed * rightSpeed, rightSpeed);
        }

        leftMotor.set(ControlMode.PercentOutput, leftSpeed * maxOutput);
        rightMotor.set(ControlMode.PercentOutput, -rightSpeed * maxOutput);
    }

    /**
     * Sets the QuickStop speed threshold in curvature drive.
     *
     * <p>QuickStop compensates for the robot's moment of inertia when stopping after a QuickTurn.
     *
     * <p>While QuickTurn is enabled, the QuickStop accumulator takes on the rotation rate value
     * outputted by the low-pass filter when the robot's speed along the X axis is below the
     * threshold. When QuickTurn is disabled, the accumulator's value is applied against the computed
     * angular power request to slow the robot's rotation.
     *
     * @param threshold X speed below which quick stop accumulator will receive rotation rate values
     *                  [0..1.0].
     */
    public void setQuickStopThreshold(double threshold) {
        quickStopThreshold = threshold;
    }

    /**
     * Sets the low-pass filter gain for QuickStop in curvature drive.
     *
     * <p>The low-pass filter filters incoming rotation rate commands to smooth out high frequency
     * changes.
     *
     * @param alpha Low-pass filter gain [0.0..2.0]. Smaller values result in slower output changes.
     *              Values between 1.0 and 2.0 result in output oscillation. Values below 0.0 and
     *              above 2.0 are unstable.
     */
    public void setQuickStopAlpha(double alpha) {
        quickStopAlpha = alpha;
    }

    /**
     * Change the default value for deadband scaling. The default value is
     * {@value team.gif.robot.Globals#DEFAULT_DEADBAND}. Values
     * smaller then the deadband are set to 0, while values larger then the
     * deadband are scaled from 0.0 to 1.0.
     *
     * @param deadband The deadband to set.
     */
    public void setDeadband(double deadband) {
        this.deadband = deadband;
    }

    /**
     * Configure the scaling factor for using drive methods with motor controllers in a mode other
     * than PercentVbus or to limit the maximum output.
     *
     * <p>The default value is {@value team.gif.robot.Globals#MAX_OUTPUT}.
     *
     * @param maxOutput Multiplied with the output percentage computed by the drive functions.
     */
    public void setMaxOutput(double maxOutput) {
        this.maxOutput = maxOutput;
    }
}
