package com.han.practise.PortionPersist;

import java.io.Serializable;

/**
 * @Author: hanyf
 * @Description: 薪水类
 * @Date: Created by in 15:28 2017/8/30
 */
public class Salary implements Serializable {
    private static final long serialVersionUID = 8829891628143801133L;

    //基本工资
    private int basePay;
    //绩效工资
    private int bonus;

    public Salary(int basePay, int bonus) {
        this.basePay = basePay;
        this.bonus = bonus;
    }

    public int getBasePay() {
        return basePay;
    }

    public void setBasePay(int basePay) {
        this.basePay = basePay;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}
