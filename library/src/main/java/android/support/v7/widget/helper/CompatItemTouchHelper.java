package android.support.v7.widget.helper;


/**
 * Created by luwenxiang on 17-12-22.
 */

public class CompatItemTouchHelper extends ItemTouchHelper {

    public CompatItemTouchHelper(ItemTouchHelper.Callback callback) {
        super(callback);
    }

    /**
     * Developer callback which controls the behavior of ItemTouchHelper.
     *
     * @return {@link Callback}
     */
    public ItemTouchHelper.Callback getCallback() {
        return mCallback;
    }
}
