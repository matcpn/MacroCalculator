package com.example.macrocalculator;

import android.app.Activity;

/**
 * Created by matcp_000 on 2/6/2015.
 */
public class User {
    //The Mifflin St Jeor Equation for calculating BMR
    Height m_height;
    Weight m_weight;
    boolean m_isMale;
    boolean m_isGaining;
    int m_age;
    int m_s;
    float m_bmr;
    float m_TDEE;
    float m_caloriesToEat;

    public User(Height h, Weight w, boolean isMale, boolean isGaining, int age, ActivityMultiplier a) {
        m_height = h;
        m_weight = w;
        m_isMale = isMale;
        m_age = age;
        m_isGaining = isGaining;
        m_s = isMale ? 5 : -161;
        m_bmr = calcBMR(h, w, m_s, age);
        m_TDEE = calcTDEE(m_bmr, a);
        m_caloriesToEat = calcCaloriesToEat(m_TDEE);
    }

    public User() {

    }

    private float calcCaloriesToEat(float TDEE) {
        return (m_isGaining? TDEE * 1.2f : TDEE * .8f);
    }

    private float calcTDEE(float bmr, ActivityMultiplier a) {
        return bmr * ActivityMultiplier.getMultiplier(a);
    }

    /*enum activityMultiplier {
        float Sedentary = 1.2f; //(little or no exercise, desk job)
        float lightlyActive = 1.375f; //(light exercise/sports 1-3 days/wk)
        float moderatelyActive = 1.55f; //(moderate exercise/sports 3-5 days/wk)
        float veryActive = 1.725f; //(hard exercise/sports 6-7 days/wk)
        float extremelyActive = 1.9f; //(hard daily exercise/sports & physical job or 2X day training, i.e.
                //marathon, contest, etc.)
    }*/

    enum ActivityMultiplier {
        SEDENTARY, //(little or no exercise, desk job)
        LIGHTLYACTIVE, //(light exercise/sports 3 days/wk)
        SOMEWHATACTIVE, // exercise 4days/week
        MODERATELYACTIVE, //(moderate exercise/sports 5 days/wk)
        VERYACTIVE, //(hard exercise/sports 6-7 days/wk)
        EXTREMELYACTIVE; //(hard daily exercise/sports & physical job or 2X day training, i.e.
        //marathon, contest, etc.)

        public static float getMultiplier(ActivityMultiplier activityLevel) {
            switch (activityLevel) {
                case SEDENTARY:
                    return 1.2f;
                case LIGHTLYACTIVE:
                    return 1.375f;
                case SOMEWHATACTIVE:
                    return 1.42f;
                case MODERATELYACTIVE:
                    return 1.5f;
                case VERYACTIVE:
                    return 1.75f;
                case EXTREMELYACTIVE:
                    return 1.9f;
                default:
                    return 1.2f;
            }
        }
    }


    private float calcBMR(Height h, Weight w, int s, int age) {
        float bmr = ((10f * w.getWeight()) + (6.25f * h.getHeight()) - (5 * age)) + s;
        return bmr;
    }

    public float getCaloriesToEat() {
        return m_caloriesToEat;
    }
}
