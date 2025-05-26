package com.example.tp3;


import java.util.ArrayList;

public class DataDummy {
    public static ArrayList<Feed> feeds = generateDummyFeeds();
    public static ArrayList<FeedProfile> feedProfiles = generateDummyFeedProfiles();


    public static ArrayList<Story> stories = generateDummyStory();

    private static ArrayList<Feed> generateDummyFeeds() {
        ArrayList<Feed> feeds = new ArrayList<>();

        feeds.add(new Feed("angry.kitty", "kitty", "Just chil", R.drawable.img9, R.drawable.img1, R.drawable.img28));
        feeds.add(new Feed("little.dino", "dino", "😶‍🌫️", R.drawable.img10, R.drawable.img5, R.drawable.img29));
        feeds.add(new Feed("hamster.gangster", "hams",  "lorem ipsum", R.drawable.img8, R.drawable.img11, R.drawable.img27));
        feeds.add(new Feed("meng.oding", "meng", "Look at my husband", R.drawable.img6, R.drawable.img12, R.drawable.img26));
        feeds.add(new Feed("peter.ptr", "parker", "hellow there", R.drawable.img7, R.drawable.img13, R.drawable.img25));
        feeds.add(new Feed("mini.spider.man", "spidey", "bread on the board", R.drawable.img4, R.drawable.img14, R.drawable.img22));
        feeds.add(new Feed("meow.meow", "rawr", "looks like us", R.drawable.img3, R.drawable.img15, R.drawable.img23));
        feeds.add(new Feed("foster.dvd", "davi","almost there", R.drawable.img5, R.drawable.img16, R.drawable.img24));
        feeds.add(new Feed("meow.meow", "rawr", "looks like us", R.drawable.img3, R.drawable.img15, R.drawable.img23));
        feeds.add(new Feed("foster.dvd", "davi","almost there", R.drawable.img5, R.drawable.img16, R.drawable.img24));

        return feeds;
    }

    private static ArrayList<FeedProfile> generateDummyFeedProfiles() {
        ArrayList<FeedProfile> feedProfiles = new ArrayList<>();
        String uriBase = "android.resource://com.example.tp3/";
        feedProfiles.add(new FeedProfile(uriBase + R.drawable.img9));
        feedProfiles.add(new FeedProfile(uriBase + R.drawable.img10));
        feedProfiles.add(new FeedProfile(uriBase + R.drawable.img15));
        feedProfiles.add(new FeedProfile(uriBase + R.drawable.img16));
        feedProfiles.add(new FeedProfile(uriBase + R.drawable.img19));

        return feedProfiles;
    }

    private static ArrayList<Story> generateDummyStory() {
        ArrayList<Story> stories = new ArrayList<>();

        stories.add(new Story("🎸", R.drawable.img3));
        stories.add(new Story("🤏🏻", R.drawable.img4));
        stories.add(new Story("❤️", R.drawable.img2));
        stories.add(new Story("🎸", R.drawable.img6));
        stories.add(new Story("🤏🏻", R.drawable.img7));
        stories.add(new Story("❤️", R.drawable.img8));

        return stories;
    }
}
