package com.example.gradient.observer;


public class ThreadSubject {
    private Observer observer;
    private  double progress;

    public void setObserver(Observer observer){
        this.observer = observer;

    }

    public  void setProgress(double progress){
        this.progress = progress;
        notifyObserver();
    }

    private void notifyObserver(){
        if (observer != null){
            observer.update(progress);
        }
    }
}
