package dev.gallon.algorithms;

import java.util.ArrayList;

public class ArbitraryColorationNode extends CycleColorationNode {

    ArrayList<AlgoData> dataList;

    @Override
    public void onStart() {
        dataList = new ArrayList<>();
        for (int k = 1; k <= GraphProperties.DEG_MAX + 1; k++) {
            dataList.add(new AlgoData().init());
        }

        x = getID();
    }

    @Override
    public void onClock() {
        for (int k = 1; k <= GraphProperties.DEG_MAX + 1; k++) {
            coloration(k, dataList.get(k-1));
        }
    }
}
