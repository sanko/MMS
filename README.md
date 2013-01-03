# MMS

This is the stock Android 4.1 MMS (and SMS) app backported to ICS, and converted to only use public APIs.
Actually the SMS `ContentProvider` is still private but that is unavoidable.

It's in the Play store - search for "Jellybean SMS".

## Known Bugs

I don't have time to work on this anymore. I'm mainly uploading it so people can fix the following bugs:

* MMS doesn't work.
* Notifications are very hit-and-miss. Really the only way to fix it properly is to make a private SMS `ContentProvider` rather than trying to coexist harmoniously with the system one.
