package com.xenecompany.xinihostel;

import androidx.appcompat.app.AppCompatActivity;

public class pgDetails extends AppCompatActivity {
    /*private ArrayList<pgDetailsModel> data;
    private ViewPager2 pager;
    private TabLayout tablayout;
    private LinearLayout linearLayout ,linearLayout1;
    private ImageView utilitiesIcon;
    private TextView nameOfPg ,areaOfPg ,rent ,description;
    private RatingBar ratingBar;
    private ScrollView scrollView;
    Integer[] icons={R.drawable.ic_bed,R.drawable.ic_cabinet,R.drawable.ic_desk,
                     R.drawable.ic_revolving_chair,R.drawable.ic_washing_machine,
                     R.drawable.ic_spoon};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_details);
        setViews();
        setValues();
        viewPagerConfig();
        setUtilitiesIcon();
    }

    private void setViews(){
        ratingBar = (RatingBar) findViewById(R.id.layoutPgPictures_RatingBar);
        nameOfPg = (TextView) findViewById(R.id.layoutPgPictures_NameOfPg);
        areaOfPg = (TextView) findViewById(R.id.layoutPgPictures_AreaOfPg);
        rent = (TextView) findViewById(R.id.layoutPgPictures_Rent);
        description = (TextView) findViewById(R.id.layoutPgDescription_Description);
    }

    private void setValues(){
        ratingBar.setRating(2.5f);
        nameOfPg.setText("XYZ");
        areaOfPg.setText("area of pg");
        rent.setText("4000/-");
        description.setText(R.string.layout_pg_description_filler_text);
    }

    private void setUtilitiesIcon(){
        linearLayout = findViewById(R.id.pgPicturesUtilitiesLayout);
        for(int i=0;i<5;i++){
            utilitiesIcon = new ImageView(this);
            utilitiesIcon.setImageResource(R.drawable.bed);
            utilitiesIcon.setPadding(5,5,5,5);
            linearLayout.addView(utilitiesIcon);
        }
    }

    private void viewPagerConfig(){
        data = new ArrayList<>(4);
        data.add(new pgDetailsModel(R.drawable.demo_hostel));
        data.add(new pgDetailsModel(R.drawable.demo_hostel));
        data.add(new pgDetailsModel(R.drawable.demo_hostel));
        data.add(new pgDetailsModel(R.drawable.demo_hostel));

        pager = findViewById(R.id.viewPager22);
        pager.setAdapter(new pgDetailsAdapter(data,this));

//        CompositePageTransformer transformer = new CompositePageTransformer();
//        transformer.addTransformer(new MarginPageTransformer((8)));
//        transformer.addTransformer(new ViewPager2.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                float v = 1 - Math.abs(position);
//                page.setScaleY(0.8f+v*0.2f);
//            }
//        });
//        pager.setPageTransformer(transformer);

//        tablayout = (TabLayout) findViewById(R.id.layoutPgPicturesTabLayout);
//        new TabLayoutMediator(tablayout ,pager ,
//                (tab , position) -> tab.select()).attach();
    }
*/
}