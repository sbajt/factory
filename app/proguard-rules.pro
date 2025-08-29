-keep class com.sbajt.matscounter.ui.viewModels.MainScreenViewModel { <init>(...); }
# Or more generally for all ViewModels
-keepclassmembers public class * extends androidx.lifecycle.ViewModel {
   public <init>(...);
}
