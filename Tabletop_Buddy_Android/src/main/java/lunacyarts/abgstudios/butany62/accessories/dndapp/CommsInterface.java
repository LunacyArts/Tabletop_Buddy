package lunacyarts.abgstudios.butany62.accessories.dndapp;

import java.io.IOException;

/**
 * Created by Tyler Helwig on 5/13/2017.
 */

public interface CommsInterface {
    void handleFail(Exception e, int taskID);

    void handleSuccess(String s, int taskID);
}
