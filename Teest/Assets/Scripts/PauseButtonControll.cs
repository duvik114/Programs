using UnityEngine;
using UnityEngine.UI;
using UnityEngine.EventSystems;

public class PauseButtonControll : MonoBehaviour, IPointerDownHandler//, IPointerUpHandler
{

    public GameObject playButton;
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
        if (!gc.getPause())
        {
            gc.setGamePause(true);
        }
        playButton.SetActive(true);
    }
}
