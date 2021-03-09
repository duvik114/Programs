using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameControll : MonoBehaviour
{

    public int powerTime = 20;
    private int powerTimeCounter;
    private float gameSpeed = 18f;
    private float speedClone = 18f;
    private bool isPowerMode = false;
    private bool pause = false;
    private bool stop = false;

    void Start()
    {
        powerTimeCounter = powerTime;
    }

    void Update()
    {
        if (isPowerMode) {
            stop = false;
            if (powerTimeCounter == 0) {
                powerTimeCounter = powerTime;
                isPowerMode = false;
                gameSpeed /= 4f;
            } else {
                if (powerTimeCounter == powerTime) {
                    gameSpeed *= 4f;
                }
                powerTimeCounter -= 1;
            }
        } else if (stop) {
            //speedClone = gameSpeed;
            gameSpeed = 0f;
            speedClone = 0f;
            isPowerMode = false;
        } else if (!(gameSpeed >= 100f || pause)) {
            gameSpeed += 0.004f;
        }
    }

    public void setStop() { //
        if (!isPowerMode) {
            stop = true;
        } else {
            stop = false;
        }
    }
    public bool getStop()
    {
        return stop;
    }

    public bool getPause() {
        return pause;
    }
    public void setPowerMode() {
        if (!pause && !stop)
        {
            isPowerMode = true;
        }
    }

    public bool getPowerMode() {
        return isPowerMode;
    }

    public void setGamePause(bool b)
    {
        if (b)
        {
            pause = true;
            speedClone = gameSpeed;
            gameSpeed = 0f;
        }
        else
        {
            pause = false;
            gameSpeed = speedClone;
        }
    }

    public float getSpeed() {
        return gameSpeed;
    }
}
