using UnityEngine;

public class PlayerMove : MonoBehaviour
{

    private GameObject gameControl;
    private GameControll gc;
    private BoxCollider2D bc;
    private Animator Anim;
    private int AnimCount = 0;
    //private bool collision = false;
    //private bool isGrounded;

    void Start ()
    {
        bc = GetComponent <BoxCollider2D> ();
        gameControl = GameObject.Find("GameControll");
        gc = gameControl.GetComponent<GameControll> ();
        Anim = GetComponent <Animator> ();
        //isGrounded = false;
    }

    void Update()
    {
        /*if (!gc.getPause()) {
            if (Input.GetKeyDown(KeyCode.Space)) {
                if (transform.position.y <= -1f) {
                    transform.position = new Vector2(transform.position.x, 3.15f);
                } else if (transform.position.y >= 3f) {
                    transform.position = new Vector2(transform.position.x, -1.86f);
                }
            } else if (Input.GetKeyDown(KeyCode.F)) {
                gc.setPowerMode();
            }
        }*/
        if (gc.getPause())
        {
            Anim.enabled = false;
        }
        else
        {
            Anim.enabled = true;
            if (AnimCount > 0)
            {
                AnimCount -= 1;
            }
            else
            {
                Anim.SetBool("isCollision", false);
            }
        }
        /*if (Input.GetKeyDown(KeyCode.W)) {
            if (isGrounded) {
                transform.position = new Vector2(transform.position.x, 3.18f);
            }
        }
        else if (Input.GetKeyDown(KeyCode.S)) {
            if (transform.position.y > -1.86) {
                transform.position = new Vector2(transform.position.x, -1.87f);
            }
        }*/
    }

    /*void OnCollisionEnter2D(Collision2D col) { 
        /*if (transform.position.y + bc.offset.y - bc.size.y / 2f >= col.gameObject.transform.position.y - col.gameObject.GetComponent<BoxCollider2D>().offset.y + col.gameObject.GetComponent<BoxCollider2D>().size.y / 2f) {
            isGrounded = true;
        } else {
            isGrounded = false;
        }*/
        /*if (transform.position.y > col.gameObject.transform.position.y) {
            isGrounded = true;
        } else {
            isGrounded = false;
        }
    }*/

    void OnCollisionEnter2D (Collision2D col) {
        /*if (col.gameObject.tag == "Respawn") {
            collision = true;
        }*/
        
        if (col.gameObject.tag == "Enemy") {
            Anim.SetBool("isCollision", true);
            AnimCount = 4;
        } else if (col.gameObject.tag == "Respawn") {
            if (gc.getPowerMode()) {
                Anim.SetBool("isCollision", true);
                AnimCount = 4;
            } else {
                Anim.SetTrigger("death");
                gc.setStop();
            }
        }
    }

    public void ripperDeath() {
        Destroy(this.gameObject);
    }
}
