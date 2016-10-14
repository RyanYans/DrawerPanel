package com.rya.drawerpanel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.rya.drawerpanel.view.SlideMenu;

public class DrawerPanel extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ivb_menu;
    private SlideMenu slide_menu;
    Integer i = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_drawer_panel);

        initUI();
    }

    private void initUI() {
        slide_menu = (SlideMenu) findViewById(R.id.slide_menu);
        ivb_menu = (ImageButton) findViewById(R.id.ivb_menu);
        int str = 1;

        ivb_menu.setOnClickListener(this);
    }

    public void onTabClick(View view) {

    }

    @Override
    public void onClick(View v) {
        slide_menu.switchState();
    }
}
