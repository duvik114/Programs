using UnityEngine;

public class WallsMove : MonoBehaviour
{

    //public float speed = 18f;
    private GameObject gameControl;
    private GameControll gc;
    private Rigidbody2D rb;
    private BoxCollider2D bc;
    private float width;

    void Start()
    {
        rb = GetComponent <Rigidbody2D> ();
        bc = GetComponent <BoxCollider2D> ();
        gameControl = GameObject.Find("GameControll");
        gc = gameControl.GetComponent<GameControll> ();
        width = 23.68f;
        //width = bc.size.x;
    }

    void Update()
    {
        //rb.MovePosition(rb.position - Vector2.right * speed * Time.deltaTime);
        /*if (flag && playerTransform.position.x >= transform.position.x) {
            float posX = playerTransform.position.x - (playerTransform.position.x - transform.position.x) + 22.5f;
            Vector2 spawnPosition = new Vector2(posX, transform.position.y);
            Instantiate(this.gameObject, spawnPosition, Quaternion.identity);
            flag = false;
        }*/
        if (transform.position.x <= -1.5f * width /*-36.5f*/) {
            //Destroy(this.gameObject);
            transform.position = new Vector2(width * 3f + transform.position.x /*28.1f*/, transform.position.y);
        }
        rb.velocity = new Vector2(-gc.getSpeed(), rb.velocity.y);
    }
}
