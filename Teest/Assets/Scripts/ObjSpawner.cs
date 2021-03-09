using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ObjSpawner : MonoBehaviour
{
    //public float speed = 18f;
    //public GameObject houseFloor;
    //public GameObject houseRoof;
    //public GameObject earth;
    //public int state = 1;
    private Rigidbody2D rb;
    private GameObject gameControl;
    private GameControll gc;
    //private GameObject lastCreatedPlatform;
    //private GameObject CreatedPlatform1;
    //private GameObject CreatedPlatform2;

    void Start()
    {
        rb = GetComponent <Rigidbody2D> ();
        gameControl = GameObject.Find("GameControll");
        gc = gameControl.GetComponent<GameControll> ();
        //lastCreatedPlatform = null;
        //CreatedPlatform1 = GameObject.Find("UpPlatform (2)");
        //CreatedPlatform2 = GameObject.Find("DownPlatform (2)");
    }

    void Update()
    {
        rb.velocity = new Vector2(-gc.getSpeed(), rb.velocity.y);
        /*if (lastCreatedPlatform != null 
            && CreatedPlatform2.transform.position.x - 28.1f > lastCreatedPlatform.transform.position.x) {
                if (CreatedPlatform1 != null) {
                    CreatedPlatform1.transform.position = new Vector2(lastCreatedPlatform.transform.position.x + 28.1f, CreatedPlatform1.transform.position.y);
                }
                CreatedPlatform2.transform.position = new Vector2(lastCreatedPlatform.transform.position.x + 28.1f, CreatedPlatform2.transform.position.y);
        }*/
        if (transform.position.x <= 56.2) {
            /*if (state == 0) {
                int i = Random.Range(1, 5);
                if (i == 1) {state = 4;} // house
                else if (i == 2) {state = 16;} // big house
                else if (i == 3) {state = -4;} // earth
                else if (i == 4) {state = -16;} // big earth
            }
            if (state > 0) {
                lastCreatedPlatform = CreatedPlatform2;
                CreatedPlatform1 = Instantiate(houseRoof, new Vector2(transform.position.x, 0.69f), Quaternion.identity);
                CreatedPlatform2 = Instantiate(houseFloor, new Vector2(transform.position.x, -4.33f), Quaternion.identity) as GameObject;
                state -= 1;
            } else if (state < 0) {
                lastCreatedPlatform = CreatedPlatform2;
                CreatedPlatform1 = null;
                CreatedPlatform2 = Instantiate(earth, new Vector2(transform.position.x, -4.33f), Quaternion.identity) as GameObject;
                state += 1;
            } else {
              //skip  
            }*/
            transform.position = new Vector2(28.1f + transform.position.x, transform.position.y);
        }
    }
}
