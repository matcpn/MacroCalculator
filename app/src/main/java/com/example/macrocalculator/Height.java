package com.example.macrocalculator;

/**
 * Created by matcp_000 on 2/7/2015.
 * used to handle metric/imperial heights
 */
public class Height {
    float m_meters;

    Height(int feet, int inches) {
        float height;
        height = feet * 30.48f;
        height = height + (inches * 2.54f);
        m_meters = height;
    }

    Height(int meters) {
        m_meters = meters;
    }

    public float getHeight() {
        return m_meters;
    }
}
