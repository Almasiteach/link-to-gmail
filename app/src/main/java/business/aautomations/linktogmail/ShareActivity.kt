/**
 * ShareActivity.kt — LinkToGmail
 *
 * This is the sole activity in the app. It registers as a share-sheet target for
 * text/plain and text/html content. When triggered, it:
 *   1. Reads the shared text from the incoming intent.
 *   2. Tries to extract a URL (http/https) from the text via regex.
 *   3. Builds a mailto: URI addressed to gotoalma@gmail.com with subject + body.
 *   4. Fires a Gmail compose intent and immediately finishes — no UI shown.
 */

package business.aautomations.linktogmail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast

class ShareActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleShareIntent()
    }

    /**
     * Reads the shared text, derives subject + body, launches Gmail compose,
     * then finishes so this activity leaves no trace in the back-stack.
     */
    private fun handleShareIntent() {
        // Read EXTRA_TEXT; fall back to empty string if nothing was shared
        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT) ?: ""

        if (sharedText.isBlank()) {
            // Nothing to forward — bail silently
            finish()
            return
        }

        // Try to find the first http/https URL in the shared text.
        // This handles WhatsApp messages that contain a URL somewhere in the body.
        val urlRegex = Regex("https?://[^\\s]+")
        val extractedUrl = urlRegex.find(sharedText)?.value

        // Subject: "Link" when a URL is detected; otherwise first 60 chars of the text
        val subject = if (extractedUrl != null) "Link" else sharedText.take(60)

        // Body: always the full original shared text so no context is lost
        val body = sharedText

        // Build the mailto: URI — Uri.encode handles all special characters safely
        val mailtoUri = "mailto:gotoalma@gmail.com" +
            "?subject=${Uri.encode(subject)}" +
            "&body=${Uri.encode(body)}"

        // ACTION_SENDTO with mailto: opens Gmail compose directly (not a chooser)
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse(mailtoUri))

        try {
            startActivity(emailIntent)
        } catch (e: Exception) {
            // No email client installed — unlikely, but show a toast so the user knows
            Toast.makeText(this, "Could not open Gmail", Toast.LENGTH_SHORT).show()
        }

        // Finish immediately — no UI, no back-stack entry
        finish()
    }
}
