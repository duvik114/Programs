using UnityEngine;

public class EnemySpawner : MonoBehaviour
{

    public GameObject enemy;
    public GameObject wall;
    //public float speed = 18f;
    private GameObject gameControl;
    private GameControll gc;
    private Rigidbody2D rb;
    
    
    void Start()
    {
        rb = GetComponent <Rigidbody2D> ();
        gameControl = GameObject.Find("GameControll");
        gc = gameControl.GetComponent<GameControll> ();
    }

    void Update()
    {
        if (transform.position.x <= 28.1f) {
            int i = Random.Range(0, 2);
            float height;
            if (i > 0) {
                height = 3.19f;
            } else {
                height = -1.87f;
            }
            //
            i = Random.Range(0, 4);
            if (i == 0) {
                Instantiate(enemy, transform.position, Quaternion.identity);
            } else if (i == 1) {
                Instantiate(wall, transform.position, Quaternion.identity);
            }
            transform.position = new Vector2(40f, height);
        }
        rb.velocity = new Vector2(-gc.getSpeed(), rb.velocity.y);
    }
}
