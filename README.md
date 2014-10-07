##UPDATE: Problem solved!

After digging into it, I have figured it out. It did have nothing to do with `TabPageIndicator`, all because the `FragmentPagerAdapter` I used.

According to [this](http://developer.android.com/reference/android/support/v4/app/FragmentPagerAdapter.html):
>Implementation of PagerAdapter that represents each page as a Fragment that is **persistently** kept in the fragment manager **as long as the user can return to the page.**

>This version of the pager is best for use when there are a handful of typically more **static** fragments to be paged through, such as a set of tabs. The fragment of each page the user visits will **be kept in memory**, though its view hierarchy may be destroyed when not visible. This can result in using a significant amount of memory since fragment instances can hold on to an arbitrary amount of state. **For larger sets of pages, consider FragmentStatePagerAdapter.**

So i got this,:
![](https://raw.githubusercontent.com/Beeder/MyStorage/master/Image/TestTabPager/img0.PNG)

When you choose nav drawer item0 and slide among these tabs, every TAB's inner fragment `onCreateView()` , thus, all tabs' inner fragment were saved in the fragment manager. 

When you choose nav drawer item1, **only item0's outer fragment was `onDestroyView()`**, all tabs' inner fragment were saved.

So when you select nav drawer item0 again, just item0's outer fragment was `onCreateView()` again, all tabs' inner fragment did nothing!
as inner fragment's `onCreateView()` wasn't triggered, we see empty!

It is pretty easy to solve this problem if you know thing above, just **replace `FragmentPagerAdapter` with [`FragmentStatePagerAdapter`](http://developer.android.com/reference/android/support/v4/app/FragmentStatePagerAdapter.html)**.

>This version of the pager is more useful when there are a large number of pages, working more like a list view. **When pages are not visible to the user, their entire fragment may be destroyed,** only keeping the saved state of that fragment. This allows the pager to hold on to much less memory associated with each visited page as compared to FragmentPagerAdapter at the cost of potentially more overhead when switching between pages.

After using FragmentStatePagerAdapter, when you select nav drawer item0 again, all inner fragment will be re `onCreateView()` again! Thus, you can see all primary things in inner fragment.

But it's not perfect ! 

If you print all log info as i do, you will see:
 1. select nav drawer item0, TAB0's and TAB1's inner fragment were `onCreateView()`
 2. slide to TAB1, **TAB2's inner fragment was `onCreateView()` automatically but TAB0's inner fragment was not `onDestroyView()`!**  Not what the API said:

>When pages are not visible to the user, their entire fragment may be destroyed.

3. select nav drawer item1, still **only item0's outer fragment was `onDestroyView()`,**  all inner fragment did nothing (I don't know what have done).
4. select nav drawer item0 again, surprisely TAB0's and TAB1's inner fragment re `onCreateView()` again, so we can see the TextView's text.

For these who want all active inner fragment destroyed when Item0's outer fragment destroyed, you need to keep track of all the active fragment pages and destroy them all when outer fragment is destroyed. For more information, you can see [this](http://stackoverflow.com/questions/12384971/android-fragmentstatepageradapter-how-to-tag-a-fragment-to-find-it-later?answertab=votes#tab-top)

My new `TabPageIndicatorAdapter`:

```java
class TabPageIndicatorAdapter extends FragmentStatePagerAdapter {//Change from FragmentPagerAdapter to FragmentStatePagerAdapter

        Map<Integer,Fragment> integerFragmentMap = new HashMap<Integer,Fragment>();// keep track of all the "active" fragment pages.

        public TabPageIndicatorAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment=null;
            Bundle args = new Bundle();
            if(position==0)
            {
                fragment = new TabFragment0();
                args.putString("text", "This is TAB0");
                fragment.setArguments(args);
            }
            else if(position==1)
            {
                fragment = new TabFragment1();
                args.putString("text", "This is TAB1");
                fragment.setArguments(args);
            }
            else if(position==2)
            {
                fragment = new TabFragment2();
                args.putString("text", "This is TAB2");
                fragment.setArguments(args);
            }

            integerFragmentMap.put(Integer.valueOf(position),fragment);//when getItem, put it into the Map

            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);

            integerFragmentMap.remove(Integer.valueOf(position));//when destroyItem, remove it from Map
        }

        public void removeAll()
        {
            Iterator iterator = integerFragmentMap.entrySet().iterator();
            while (iterator.hasNext())
            {
                Map.Entry entry = (Map.Entry)iterator.next();
                Fragment fragment = (Fragment) entry.getValue();
                fragment.onDestroyView();//Trigger inner fragment's DestroyView() method !
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position % TITLE.length];
        }

        @Override
        public int getCount() {
            return TITLE.length;
        }
    }
```

```java

@Override
    public void onDestroyView() {
        super.onDestroyView();

        tabPageIndicatorAdapter.removeAll();//when change nav drawer item to others, auto remove all inner fragment.

        Log.d("MyTag", "NEWS--onDestroyView");
    }
```

If you want to remember which tab you chose last time, just override `onViewStateRestored(Bundle savedInstanceState)`, feel free to see my code in project [https://github.com/Beeder/TestTabPager](https://github.com/Beeder/TestTabPager)


---------------

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