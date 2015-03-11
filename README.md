# ParcelKit
This Project is used to help create parcelable with annotation.
I wrote this project in reference to Butter-Knife.
Thanks to JakeWharton and his ButterKnife project.


Usage:

you can create parcelable in two way :
1. Make your class implement parcelable and get creator from ParcelKit.
2. Do not implement parcelable , just annotate field with ParcelField , and create a delegate Parcelable with ParcelKit.toParcelable
