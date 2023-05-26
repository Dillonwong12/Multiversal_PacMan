package src.game;

/**
 * Concrete `Portal` Decorator for the `AutoPlayer`, wraps a base `AutoPlayer` to provide additional functionality by
 * indicating the presence of `Portal`s.
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import java.util.HashMap;

public class AutoPlayerPortalDecorator extends AutoPlayerDecorator{
    // The weight of each Decorator may be adjusted in the future
    private final int PORTAL_WEIGHT = 1;
    public AutoPlayerPortalDecorator(AutoPlayer autoPlayer){
        super(autoPlayer);
    }

    /**
     * Updates `tilesMap` based on the presence of `Portal`s, incrementing its weight in `tilesMap` by `PORTAL_WEIGHT`.
     */
    public void computeWeights(){
        HashMap<java.lang.Character, Integer> tilesMap = getDecoratedAutoPlayer().getTilesMap();
        tilesMap.put('i', tilesMap.getOrDefault('i', 0)+PORTAL_WEIGHT);
    }
}
