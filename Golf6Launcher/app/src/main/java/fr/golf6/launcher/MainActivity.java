package fr.golf6.launcher;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.format.DateFormat;
import android.os.Handler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {
    private final int BG = Color.rgb(8, 13, 22);
    private final int PANEL = Color.rgb(18, 27, 42);
    private final int BLUE = Color.rgb(45, 160, 255);
    private final Handler handler = new Handler();
    private TextView clock, date;
    private Runnable clockTask;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        buildScreen();
        clockTask = new Runnable() {
            @Override public void run() {
                Date now = new Date();
                clock.setText(DateFormat.format("HH:mm", now));
                date.setText(DateFormat.format("EEEE d MMMM", now));
                handler.postDelayed(this, 30000);
            }
        };
        handler.post(clockTask);
    }

    private GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color); d.setCornerRadius(radius);
        return d;
    }

    private TextView label(String text, int size, int color) {
        TextView t = new TextView(this);
        t.setText(text); t.setTextSize(size); t.setTextColor(color);
        t.setGravity(Gravity.CENTER);
        return t;
    }

    private Button button(String text) {
        Button b = new Button(this);
        b.setText(text); b.setTextColor(Color.WHITE); b.setTextSize(15);
        b.setAllCaps(false); b.setBackground(shape(Color.rgb(31, 48, 70), 18));
        return b;
    }

    private LinearLayout panel() {
        LinearLayout p = new LinearLayout(this);
        p.setOrientation(LinearLayout.VERTICAL);
        p.setGravity(Gravity.CENTER);
        p.setPadding(14, 12, 14, 12);
        p.setBackground(shape(PANEL, 22));
        return p;
    }

    private void buildScreen() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.HORIZONTAL);
        root.setPadding(16, 14, 16, 14);
        root.setBackgroundColor(BG);

        LinearLayout left = panel();
        TextView bt = label("BLUETOOTH", 15, BLUE);
        bt.setTypeface(null, Typeface.BOLD);
        left.addView(bt);
        left.addView(label("Lecteur du téléphone", 13, Color.LTGRAY));
        left.addView(label("Commandes audio", 12, Color.GRAY));
        LinearLayout controls = new LinearLayout(this);
        controls.setGravity(Gravity.CENTER);
        Button prev = button("⏮");
        Button play = button("▶ / ⏸");
        Button next = button("⏭");
        controls.addView(prev, new LinearLayout.LayoutParams(0, 58, 1));
        controls.addView(play, new LinearLayout.LayoutParams(0, 58, 1));
        controls.addView(next, new LinearLayout.LayoutParams(0, 58, 1));
        left.addView(controls);
        prev.setOnClickListener(v -> media(KeyEvent.KEYCODE_MEDIA_PREVIOUS));
        play.setOnClickListener(v -> media(KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE));
        next.setOnClickListener(v -> media(KeyEvent.KEYCODE_MEDIA_NEXT));

        LinearLayout center = panel();
        center.addView(label("GOLF 6", 15, BLUE));
        center.addView(label("--", 64, Color.WHITE));
        center.addView(label("km/h", 16, Color.LTGRAY));
        center.addView(label("Vitesse GPS : à ajouter", 11, Color.GRAY));

        LinearLayout right = panel();
        clock = label("--:--", 42, Color.WHITE);
        date = label("", 14, Color.LTGRAY);
        right.addView(clock);
        right.addView(date);
        Button apps = button("▦  Applications");
        Button settings = button("⚙  Réglages");
        right.addView(apps);
        right.addView(settings);
        apps.setOnClickListener(v -> showApps());
        settings.setOnClickListener(v -> showSettings());

        root.addView(left, new LinearLayout.LayoutParams(0, -1, 1.25f));
        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(0, -1, 1f);
        cp.setMargins(12, 0, 12, 0);
        root.addView(center, cp);
        root.addView(right, new LinearLayout.LayoutParams(0, -1, 1f));
        setContentView(root);
    }

    private void media(int key) {
        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        if (am != null) am.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, key));
        if (am != null) am.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, key));
    }

    private void showApps() {
        PackageManager pm = getPackageManager();
        Intent query = new Intent(Intent.ACTION_MAIN, null);
        query.addCategory(Intent.CATEGORY_LAUNCHER);
        List<android.content.pm.ResolveInfo> apps = pm.queryIntentActivities(query, 0);
        Collections.sort(apps, Comparator.comparing((android.content.pm.ResolveInfo a) -> a.loadLabel(pm).toString().toLowerCase()));
        List<String> names = new ArrayList<>();
        List<Intent> launches = new ArrayList<>();
        for (android.content.pm.ResolveInfo ri : apps) {
            String pkg = ri.activityInfo.packageName;
            if (pkg.equals(getPackageName())) continue;
            names.add(ri.loadLabel(pm).toString());
            Intent launch = pm.getLaunchIntentForPackage(pkg);
            launches.add(launch);
        }
        if (names.isEmpty()) names.add("Aucune application trouvée");
        String[] items = names.toArray(new String[0]);
        new AlertDialog.Builder(this).setTitle("Applications")
            .setItems(items, (dialog, which) -> {
                if (which < launches.size() && launches.get(which) != null) startActivity(launches.get(which));
            }).setNegativeButton("Fermer", null).show();
    }

    private void showSettings() {
        new AlertDialog.Builder(this)
            .setTitle("Golf 6 Launcher")
            .setMessage("Prototype 0.1\\n\\nLes commandes audio envoient des touches multimédia Android. Leur fonctionnement dépend de la compatibilité Bluetooth de l'autoradio et du téléphone. La vitesse GPS et les informations de morceau seront ajoutées après les premiers tests.")
            .setPositiveButton("OK", null).show();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (clockTask != null) handler.removeCallbacks(clockTask);
    }
}
