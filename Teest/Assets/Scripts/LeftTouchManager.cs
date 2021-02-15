using UnityEngine;
using UnityEngine.UI;
using UnityEngine.EventSystems;

public class LeftTouchManager : MonoBehaviour, IPointerDownHandler//, IPointerUpHandler
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

    public void OnPointerDown (PointerEventData eventData)
    {
        if (!gc.getStop() && !gc.getPause())
        {
            gc.setPowerMode();
        }
    }
}
