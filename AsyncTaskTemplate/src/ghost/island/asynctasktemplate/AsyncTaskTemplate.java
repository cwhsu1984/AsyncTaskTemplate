package ghost.island.asynctasktemplate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

interface AsyncTaskListener {
    public <T> void onTaskComplete(T result);

    public <T> void onTaskUpdate(T result);
}

public class AsyncTaskTemplate extends AsyncTask<Void, Integer, String> {

    private Activity activity;

    private AsyncTaskListener callback;

    private ProgressDialog dialog;

    public AsyncTaskTemplate(Activity activity) {
        this.activity = activity;
        this.callback = (AsyncTaskListener) activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = new ProgressDialog(activity);
        dialog.setMessage("Loading...");
        dialog.show();
    }

    @Override
    protected String doInBackground(Void... unused) {

        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1000);
                publishProgress(i);
            }

            return activity.toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        callback.onTaskUpdate(progress[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
        callback.onTaskComplete("job is done");
    }

}
