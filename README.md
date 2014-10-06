
I am new to android programming, recently I use navigation drawer and viewpageindicator, but i got stuck here.

Select item0, show item0 fragment with three tabs correctly, select item1, when done, select item0 again, item0 fragment with three tabs showed but **only three tabs showed**, the corresponding inner fragment was **gone!**  More strangely,  when I slide among these three tabs, TAB0's and TAB2's inner fragments showed again, **but TAB1's inner fragment never showed!** WHY?

Here is the details:
According to [this tutorial](http://www.tutecentral.com/android-custom-navigation-drawer/), I create a navigation drawer activity like this:

![](https://raw.githubusercontent.com/Beeder/MyStorage/master/Image/TestTabPager/Screenshot_2014-10-06-17-25-59.png)

In order to demonstrate,  only two drawer items were added.  when select item 0, here goes item0 fragment(which named NewsFragment in my project):

![](https://raw.githubusercontent.com/Beeder/MyStorage/master/Image/TestTabPager/Screenshot_2014-10-06-17-26-14.png)

In NewsFragment, I use JakeWharton's [Android-ViewPagerIndicator](https://github.com/JakeWharton/Android-ViewPagerIndicator), more specifically TabPageIndicator.
There are three tabs, select one tab, corresponding TabFragment(x) showed.

When select drawer item1, show:

![](https://raw.githubusercontent.com/Beeder/MyStorage/master/Image/TestTabPager/Screenshot_2014-10-06-17-26-24.png)

**But**, when I select drawer item0 **again**, everything was gone except the three tabs!  That means the TabFragment(x)'s TextView isn't showed. WHY?

**More Strangely**, After I slide among these three tabs,  TAB0's and TAB2's inner fragments(TextView) showed again, **but TAB1's inner fragment never showed!** WHY? Is that because TAB1 is in the middle? 

I have tried many ways to work it out, In NewsFragment, i override `onStop()` and `onViewStateRestored()` to get the last selected tab, but `tabPageIndicator.setCurrentItem(selectedItem);`doesn't seem to really trigger `Fragment getItem(int position)` method, the selected tab's inner fragment isn't created again!

```java
@Override
    public void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("NewsSelected",pager.getCurrentItem());
        editor.commit();

        Log.d("MyTag", "NEWS--onStop");
    }
```


```java
@Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int selectedItem=sharedPreferences.getInt("NewsSelected",0);//Default value =0
        Toast.makeText(getActivity(), "Last time, you chose TAB"+selectedItem, Toast.LENGTH_SHORT).show();
        tabPageIndicator.setCurrentItem(selectedItem);//reselect the tab last selected. but the inner fragment isn't recreated, i got empty, why?
        //pager.setAdapter(tabPageIndicatorAdapter);
        //tabPageIndicator.setViewPager(pager);

        Log.d("MyTag", "NEWS--onViewStateRestored");
    }
```


I guess it has something to do with not saving InstanceState, but i don't know what else to do, I have struggled two days, I'd appreciate it if you could help me.

Thanks again.