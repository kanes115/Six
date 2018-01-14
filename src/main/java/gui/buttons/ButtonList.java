package gui.buttons;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by michaello on 14.01.18.
 */
public class ButtonList{

    private final List<GameButton> gameButtons = new LinkedList<>();

    public ButtonList() {
    }

    public boolean add(GameButton btn){
        boolean result = gameButtons.add(btn);
        btn.setChecked(result);
        return result;
    }

    public GameButton get(int index){
        return gameButtons.get(index);
    }

    public GameButton remove(int index){
        GameButton btn = gameButtons.remove(index);
        btn.setChecked(false);
        return btn;
    }

    public boolean remove(GameButton btn){
        boolean removed = gameButtons.remove(btn);
        btn.setChecked(!removed);
        return removed;
    }

    public boolean contains(GameButton btn){
        return gameButtons.contains(btn);
    }

    public int size(){
        return gameButtons.size();
    }

    public void clearWholeList(){
        gameButtons.stream()
                .forEach(btn -> btn.setChecked(false));
        gameButtons.clear();
    }

    public void clearWholeListExceptDeckButton(){
        gameButtons.stream()
                .filter(btn -> !(btn instanceof DeckStackButton))
                .forEach(btn -> btn.setChecked(false));
        gameButtons.removeIf(btn -> !(btn instanceof DeckStackButton));
    }

}
