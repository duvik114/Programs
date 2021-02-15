using UnityEngine;

public class BackgroundMove : MonoBehaviour
{

    public GameObject backGroundWall;
    private GameObject gameControl;
    private GameControll gc;
    private Rigidbody2D rb;
    private float width = 40f;

    void Start()
    {
        rb = GetComponent <Rigidbody2D> ();
        gameControl = GameObject.Find("GameControll");
        gc = gameControl.GetComponent<GameControll> ();
    }

    void Update()
    {
        if (transform.position.x <= -width) {
            transform.position = new Vector2(2f * width + transform.position.x, transform.position.y);
        }
        rb.velocity = new Vector2(-gc.getSpeed(), rb.velocity.y);
        if (backGroundWall.transform.position.x < transform.position.x
            /*&& transform.position.x - backGroundWall.transform.position.x != 20f*/) {
            transform.position = new Vector2(backGroundWall.transform.position.x + width, transform.position.y);
        }
    }
}
