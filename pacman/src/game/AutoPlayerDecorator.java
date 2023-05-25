package src.game;

import ch.aplu.jgamegrid.Location;

public class AutoPlayerDecorator implements AutoPlayer{
    private final AutoPlayer decoratedAutoPlayer;

    public AutoPlayerDecorator(AutoPlayer autoPlayer){
        this.decoratedAutoPlayer = autoPlayer;
    }

    public AutoPlayer getDecoratedAutoPlayer() {
        return decoratedAutoPlayer;
    }

    @Override
    public void computeWeights() {
        decoratedAutoPlayer.computeWeights();
    }

    @Override
    public Location moveInAutoMode(PacActor pacActor) {
        return decoratedAutoPlayer.moveInAutoMode(pacActor);
    }
}
