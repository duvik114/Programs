using UnityEngine;

public class WallControll : MonoBehaviour
{

    //public float speed = 18f;
    private GameObject gameControl;
    private GameControll gc;
    private bool collision = false;
    private Rigidbody2D rb;
    private Animator Anim;
    private int AnimCount = 0;
    //private BoxCollider2D bc;

    void Start()
    {
        rb = GetComponent <Rigidbody2D> ();
        //bc = GetComponent <BoxCollider2D> ();
        gameControl = GameObject.Find("GameControll");
        gc = gameControl.GetComponent<GameControll> ();
        Anim = GetComponent <Animator> ();
    }

    void Update()
    {
        if (gc.getPause())
        {
            Anim.enabled = false;
        }
        else
        {
            Anim.enabled = true;//
            if (collision)
            {
                gc.setStop();
                collision = false;
            }
            if (transform.position.x <= -36.5f)
            {
                Destroy(this.gameObject);
            }
            if (AnimCount > 0)
            {
                AnimCount -= 1;
            }
            else
            {
                Anim.SetBool("isCollision", false);
            }
        }
        rb.velocity = new Vector2(-gc.getSpeed(), rb.velocity.y);
    }

    void OnCollisionEnter2D (Collision2D col) {
        if (col.gameObject.tag == "Player") {
            if (gc.getPowerMode()) {
                Destroy(this.gameObject);
            }else if (col.gameObject.transform.position.x < transform.position.x) {
                Anim.SetBool("isCollision", true);
                AnimCount = 4;
                collision = true;
            }
        } else if (col.gameObject.tag == "Enemy") {
            Anim.SetBool("isCollision", true);
            AnimCount = 4;
        }
    }
}
