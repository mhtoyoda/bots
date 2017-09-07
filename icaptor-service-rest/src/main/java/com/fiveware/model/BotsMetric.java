package com.fiveware.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;

@AutoProperty
public class BotsMetric implements Serializable {
    private int amount;
    private int success;
    private int error;
    private int processed;


    public BotsMetric() {
    }

    public BotsMetric(int amount, int success, int error, int processed) {
        this.amount = amount;
        this.success = success;
        this.error = error;
        this.processed = processed;
    }

    public int getAmount() {
        return amount;
    }

    public int getSuccess() {
        return success;
    }

    public int getError() {
        return error;
    }

    public int getProcessed() {
        return processed;
    }


    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }

    public static class BuilderBotsMetric{
        private int amount;
        private int success;
        private int error;
        private int processed;
        private BotsMetric botsMetric;


        public BuilderBotsMetric addAmount(int amount){
            this.amount=amount;
            return this;
        }

        public BuilderBotsMetric addSucess(int success){
            this.success += success;
            return this;
        }

        public BuilderBotsMetric addError(int error){
            this.error += error;
            return this;
        }

        public BuilderBotsMetric addProcessed(int processed){
            this.processed += processed;
            return this;
        }

        public BotsMetric get() {
            return this.botsMetric;
        }

        public BotsMetric build(){
            this.botsMetric = new BotsMetric(this.amount,this.success,this.error,this.processed);
            return this.botsMetric;
        }


    }

}
