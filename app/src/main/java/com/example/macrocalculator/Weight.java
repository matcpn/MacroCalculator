package com.example.macrocalculator;

/**
 * Created by matcp_000 on 2/7/2015.
 * used to handle metric/imperial weights
 */
public class Weight {
    float m_kilograms;

    public Weight(float weight, boolean imperial) {
        if (imperial) {
            m_kilograms = weight * 0.453592f;
        }
        else {
            m_kilograms = weight;
        }
    }

    public float getWeight() {
        return m_kilograms;
    }
}
