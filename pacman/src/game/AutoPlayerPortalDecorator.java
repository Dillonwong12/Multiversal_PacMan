package src.game;

import java.util.HashMap;

public class AutoPlayerPortalDecorator extends AutoPlayerDecorator{

    private final int PORTAL_WEIGHT = 1;
    public AutoPlayerPortalDecorator(AutoPlayer autoPlayer){
        super(autoPlayer);
    }

    public void computeWeights(){
        HashMap<java.lang.Character, Integer> tilesMap = getDecoratedAutoPlayer().getTilesMap();
        tilesMap.put('i', tilesMap.getOrDefault('i', 0)+PORTAL_WEIGHT);
    }
}
