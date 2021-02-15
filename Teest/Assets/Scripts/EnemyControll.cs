using UnityEngine;

public class EnemyControll : MonoBehaviour
{
    
    //public float speed = 18f;
    private GameObject gameControl;
    private GameControll gc;
    private Rigidbody2D rb;
    private Animator Anim;
    //private bool isDead = false;

    void Start()
    {
        rb = GetComponent <Rigidbody2D> ();
        gameControl = GameObject.Find("GameControll");
        gc = gameControl.GetComponent<GameControll> ();
        Anim = GetComponent <Animator> ();
    }

    void Update()
    {
        if (gc.getPause())
        {
            Anim.enabled = false;
            rb.velocity = new Vector2(-gc.getSpeed(), rb.velocity.y);//
        }
        else
        {
            Anim.enabled = true;//
            if (transform.position.x <= -36.5f)
            {
                Destroy(this.gameObject);
            }
            if (transform.position.y <= -10f)
            {
                Destroy(this.gameObject);
            }
            if (transform.position.x >= 36.5f)
            {
                Destroy(this.gameObject);
            }
            rb.velocity = new Vector2(-gc.getSpeed() + 4f, rb.velocity.y);
        }
    }

    void OnCollisionEnter2D (Collision2D col) {
        if (col.gameObject.tag == "Player"
            || col.gameObject.tag == "Respawn") {
            //Destroy(this.gameObject);
            Anim.SetTrigger("dieTrig");
        }
    }

    public void ghostDeath() {
        Destroy(this.gameObject);
    }
    
}
