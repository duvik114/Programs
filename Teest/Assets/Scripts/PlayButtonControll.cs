using UnityEngine;
using UnityEngine.UI;
using UnityEngine.EventSystems;

public class PlayButtonControll : MonoBehaviour, IPointerDownHandler//, IPointerUpHandler
{

    private GameObject gameControl;
    private GameControll gc;
    private GameObject player;

    void Start()
    {
        gameControl = GameObject.Find("GameControll");
        gc = gameControl.GetComponent<GameControll>();
        player = GameObject.Find("ripper");
        gameObject.SetActive(false);
    }

    /*void Update()
    {
        if (gc.getPause())
        {
            transform.position = new Vector2(-5f, -12.5f);
        }
        else
        {
            transform.position = new Vector2(-5f, 400f);
        }
    }*/

    public void OnPointerDown(PointerEventData eventData)
    {
        gc.setGamePause(false);
        gameObject.SetActive(false);
    }
}
