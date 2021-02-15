using UnityEngine;
using UnityEngine.UI;
using UnityEngine.EventSystems;

public class RightTouchManager : MonoBehaviour, IPointerDownHandler//, IPointerUpHandler
{

    private GameObject gameControl;
    private GameControll gc;
    private GameObject player;

    void Start()
    {
        gameControl = GameObject.Find("GameControll");
        gc = gameControl.GetComponent<GameControll>();
        player = GameObject.Find("ripper");
    }

    public void OnPointerDown(PointerEventData eventData)
    {
        if (!gc.getStop() && !gc.getPause())
        {
            if (player.transform.position.y <= -1f)
            {
                player.transform.position = new Vector2(player.transform.position.x, 3.15f);
            }
            else if (player.transform.position.y >= 3f)
            {
                player.transform.position = new Vector2(player.transform.position.x, -1.86f);
            }
        }
    }
}
