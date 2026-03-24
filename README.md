# LinkToGmail

Android share-sheet target that forwards any shared text or URL directly to Gmail compose, addressed to `gotoalma@gmail.com`.

## What it does

1. Appears in the Android share sheet for **text/plain** and **text/html** content.
2. Extracts the first `http(s)://` URL from the shared text if present.
3. Opens Gmail compose with:
   - **To:** `gotoalma@gmail.com`
   - **Subject:** `Link` (if a URL was found) or the first 60 chars of the text
   - **Body:** the full original shared text
4. Finishes immediately — no UI, no back-stack entry.

## Requirements

- Android 8.0+ (API 26)
- Gmail app installed

## Build

GitHub Actions builds an unsigned release APK on every push to `main`.
Download from the **Releases** tab or the **Actions → Artifacts** section.

To install (sideload):
```
adb install app-release-unsigned.apk
```
Or transfer the APK to the device and open it with a file manager (enable "Install unknown apps" for that app).

## Package

`business.aautomations.linktogmail`
