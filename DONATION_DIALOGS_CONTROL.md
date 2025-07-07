# Donation Dialogs Control

This document explains how to control the donation dialogs in Fossify-Commons.

## Overview

The Fossify-Commons library includes donation dialogs that can appear in apps:
- **PurchaseThankYouDialog**: Shows when users try to use "Apply to All" feature without the Thank You app
- **DonateDialog**: Shows periodically (every 30 app runs) to encourage donations

**By default, donation dialogs are DISABLED** and will not show up in your apps.

## How to Control Donation Dialogs

### Method 1: Enable Donation Dialogs (if needed)

By default, donation dialogs are disabled. If you want to enable them, add this line in your Activity's `onCreate()` method:

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    // Enable donation dialogs (optional - they're disabled by default)
    enableDonationDialogs()
    
    // ... rest of your onCreate code
}
```

### Method 2: Enable/Disable Dynamically

You can control donation dialogs at runtime:

```kotlin
// Disable donation dialogs (default behavior)
disableDonationDialogs()

// Enable donation dialogs
enableDonationDialogs()

// Check if donation dialogs are disabled
val areDisabled = areDonationDialogsDisabled()

// Check if donation dialogs are enabled
val areEnabled = areDonationDialogsEnabled()
```

### Method 3: Direct Configuration Access

You can also directly access the configuration:

```kotlin
// Disable (default behavior)
baseConfig.disableDonationDialogs = true

// Enable
baseConfig.disableDonationDialogs = false

// Check
val areDisabled = baseConfig.disableDonationDialogs
```

## What Gets Disabled

When donation dialogs are disabled (default behavior):

1. **PurchaseThankYouDialog** - Will not show when users try to use "Apply to All" feature
2. **DonateDialog** - Will not show periodically (every 30 app runs)
3. **Compose Donation Dialogs** - Will not show in Jetpack Compose apps

## Example Implementation

See the sample app (`samples/src/main/kotlin/org/fossify/commons/samples/activities/MainActivity.kt`) for a complete example.

## Notes

- **By default, donation dialogs are DISABLED** - no action needed to disable them
- The setting is persisted in SharedPreferences
- Donation dialogs will not show until explicitly enabled
- This affects all donation-related dialogs across the entire app 