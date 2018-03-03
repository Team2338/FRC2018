package team.gif.lib;

import edu.wpi.first.wpilibj.command.CommandGroup;

public abstract class GameDataCommandGroup extends CommandGroup{

    String gameData = "";

    public abstract void setGameData(String gameData);
}
