package com.fiveware.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;

@AutoProperty
public class BotsMetric implements Serializable {
    private long amount;
    private long success;
    private long error;
    private long processed;


    public BotsMetric() {
    }

    public BotsMetric(long amount, long success, long error, long processed) {
        this.amount = amount;
        this.success = success;
        this.error = error;
        this.processed = processed;
    }

    public long getAmount() {
        return amount;
    }

    public long getSuccess() {
        return success;
    }

    public long getError() {
        return error;
    }

    public long getProcessed() {
        return processed;
    }


    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }

    public static class BuilderBotsMetric{
        private long amount;
        private long success;
        private long error;
        private long processed;
        private BotsMetric botsMetric;


        public BuilderBotsMetric addAmount(long amount){
            this.amount=amount;
            return this;
        }

        public BuilderBotsMetric addSuccess(long success){
            this.success += success;
            this.processed = (long) (((Float.valueOf(error) + Float.valueOf(success))/Float.valueOf(amount)) * 100.0);
            return this;
        }

        public BuilderBotsMetric addError(long error){
            this.error += error;
            this.processed = (long) (((Float.valueOf(error) + Float.valueOf(success))/Float.valueOf(amount)) * 100.0);
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

    public static void main(String[] args) {
        int amount = 5;
        int success = 2;
        int error = 2;
        int processed = (int) (((Float.valueOf(error) + Float.valueOf(success))/Float.valueOf(amount)) * 100.0);

        System.out.println("processed = " + processed);




    }

}
