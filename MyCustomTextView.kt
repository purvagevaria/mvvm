package com.pg.scoalview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.text.*
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView
import com.hendraanggrian.appcompat.internal.SocialViewImpl
import com.hendraanggrian.appcompat.socialview.Mention
import com.hendraanggrian.appcompat.socialview.Mentionable
import com.hendraanggrian.appcompat.widget.SocialView
import com.hendraanggrian.appcompat.widget.SocialView.OnChangedListener
import java.util.*


/**
 * [android.widget.MultiAutoCompleteTextView] with hashtag, mention, and hyperlink support.
 *
 * @see SocialView
 */
class MyCustomTextView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.autoCompleteTextViewStyle) : AppCompatMultiAutoCompleteTextView(context, attrs, defStyleAttr), SocialView {
    private val impl: SocialView
    var isCallWs = false
    var startIndex = 0
    var defaultMentionAdapter: SocialArrayAdapter<Mentionable>? = null
    var stringToDisaplay = ""

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (s.toString().isEmpty()) {
                isCallWs = false
            }
            if (!TextUtils.isEmpty(s) && start < s.length) {
                when (s[start]) {
                    '@' -> {
                        startIndex = start
                        isCallWs = true

                    }
                }
            }
        }

        override fun afterTextChanged(s: Editable) {
            if (s.isNotEmpty()) {
                if (s.length <= startIndex) {
                    isCallWs = false
                    return
                }
                if (!s[startIndex].toString().contains("@")) {
                    isCallWs = false
                    startIndex = 0
                    return
                }
                if (isCallWs) {
                    val lastIndex = s.lastIndexOf("@")
                    val toIndex = s.length
                    val stringToPass = s.substring(lastIndex + 1, toIndex)
                    if (stringToPass.length > 2) {
                        callWebservice(stringToPass)
                    }
                }
            }


        }
    }

    private fun callWebservice(name: String) {
        Log.d("Tag ", "Call webservice $name")
        defaultMentionAdapter!!.clear()
        defaultMentionAdapter = SocialArrayAdapter<Mentionable>(context!!)
        for (i in 1..3) {
            defaultMentionAdapter!!.add(Mention("$name $i"))
        }

        setAdapter(defaultMentionAdapter)
    }


    /**
     * {@inheritDoc}
     */
    override fun isHashtagEnabled(): Boolean {
        return impl.isHashtagEnabled
    }

    /**
     * {@inheritDoc}
     */
    override fun setHashtagEnabled(enabled: Boolean) {
        impl.isHashtagEnabled = enabled
        setTokenizer(CharTokenizer())
    }

    /**
     * {@inheritDoc}
     */
    override fun isMentionEnabled(): Boolean {
        return true
    }

    /**
     * {@inheritDoc}
     */
    override fun setMentionEnabled(enabled: Boolean) {
        impl.isMentionEnabled = enabled
        setTokenizer(CharTokenizer())
    }

    /**
     * {@inheritDoc}
     */
    override fun isHyperlinkEnabled(): Boolean {
        return impl.isHyperlinkEnabled
    }

    /**
     * {@inheritDoc}
     */
    override fun setHyperlinkEnabled(enabled: Boolean) {
        impl.isHyperlinkEnabled = enabled
    }

    /**
     * {@inheritDoc}
     */
    override fun getHashtagColors(): ColorStateList {
        return impl.hashtagColors
    }

    /**
     * {@inheritDoc}
     */
    override fun setHashtagColors(colors: ColorStateList) {
        impl.hashtagColors = colors
    }

    /**
     * {@inheritDoc}
     */
    override fun getMentionColors(): ColorStateList {
        return impl.mentionColors
    }

    /**
     * {@inheritDoc}
     */
    override fun setMentionColors(colors: ColorStateList) {
        impl.mentionColors = colors
    }

    /**
     * {@inheritDoc}
     */
    override fun getHyperlinkColors(): ColorStateList {
        return impl.hyperlinkColors
    }

    /**
     * {@inheritDoc}
     */
    override fun setHyperlinkColors(colors: ColorStateList) {
        impl.hyperlinkColors = colors
    }

    /**
     * {@inheritDoc}
     */
    override fun getHashtagColor(): Int {
        return impl.hyperlinkColor
    }

    /**
     * {@inheritDoc}
     */
    override fun setHashtagColor(color: Int) {
        impl.hyperlinkColor = color
    }

    /**
     * {@inheritDoc}
     */
    override fun getMentionColor(): Int {
        return impl.mentionColor
    }

    /**
     * {@inheritDoc}
     */
    override fun setMentionColor(color: Int) {
        impl.mentionColor = color
    }

    /**
     * {@inheritDoc}
     */
    override fun getHyperlinkColor(): Int {
        return impl.hyperlinkColor
    }

    /**
     * {@inheritDoc}
     */
    override fun setHyperlinkColor(color: Int) {
        impl.hyperlinkColor = color
    }

    /**
     * {@inheritDoc}
     */
    override fun setOnHashtagClickListener(listener: SocialView.OnClickListener?) {
        impl.setOnHashtagClickListener(listener)
    }

    /**
     * {@inheritDoc}
     */
    override fun setOnMentionClickListener(listener: SocialView.OnClickListener?) {
        impl.setOnMentionClickListener(listener)
    }

    /**
     * {@inheritDoc}
     */
    override fun setOnHyperlinkClickListener(listener: SocialView.OnClickListener?) {
        impl.setOnHashtagClickListener(listener)
    }

    /**
     * {@inheritDoc}
     */
    override fun setHashtagTextChangedListener(listener: OnChangedListener?) {
        impl.setHashtagTextChangedListener(listener)
    }

    /**
     * {@inheritDoc}
     */
    override fun setMentionTextChangedListener(listener: OnChangedListener?) {
        impl.setMentionTextChangedListener(listener)
    }

    /**
     * {@inheritDoc}
     */
    override fun getHashtags(): List<String> {
        return impl.hashtags
    }

    /**
     * {@inheritDoc}
     */
    override fun getMentions(): List<String> {
        return impl.mentions
    }

    /**
     * {@inheritDoc}
     */
    override fun getHyperlinks(): List<String> {
        return impl.hyperlinks
    }

    /**
     * While `CommaTokenizer` tracks only comma symbol,
     * `CharTokenizer` can track multiple characters, in this instance, are hashtag and at symbol.
     *
     * @see android.widget.MultiAutoCompleteTextView.CommaTokenizer
     */
    private inner class CharTokenizer internal constructor() : Tokenizer {
        private val chars: MutableCollection<Char> = ArrayList()

        override fun findTokenStart(text: CharSequence, cursor: Int): Int {
            var i = cursor
            while (i > 0 && !chars.contains(text[i - 1])) {
                i--
            }
            while (i < cursor && text[i] == ' ') {
                i++
            }
            // imperfect fix for dropdown still showing without symbol found
            if (i == 0 && isPopupShowing) {
                dismissDropDown()
            }
            return i
        }

        override fun findTokenEnd(text: CharSequence, cursor: Int): Int {
            var i = cursor
            val len = text.length
            while (i < len) {
                if (chars.contains(text[i])) {
                    return i
                } else {
                    i++
                }
            }
            return len
        }

        override fun terminateToken(text: CharSequence): CharSequence {
            var i = text.length
            while (i > 0 && text[i - 1] == ' ') {
                i--
            }
            return if (i > 0 && chars.contains(text[i - 1])) {
                text
            } else {
                val str = SpannableStringBuilder(text)
                str.setSpan(StyleSpan(Typeface.BOLD), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                "$text "
                /* if (text is Spanned) {
                     val sp: Spannable = SpannableString("$text ")
                     TextUtils.copySpansFrom(text, 0, text.length, Any::class.java, sp, 0)
                     val boldSpan = StyleSpan(Typeface.BOLD)
                     sp.setSpan(boldSpan, 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                     sp
                 } else {
                     "$text "
                 }*/
            }
        }

        init {

            if (isMentionEnabled) {
                chars.add('@')
            }
        }
    }

    init {
        impl = SocialViewImpl(this, attrs)
        addTextChangedListener(textWatcher)
        setTokenizer(CharTokenizer())
        setOnItemClickListener { adapterView, view, i, l ->
            Log.d("Tag", "Item click ")
            /*var getText = text.toString().lastIndexOf("@")
            var length = text.length
            var string = text.substring(getText, length)
            var remainingString = text.toString().removeRange(getText, string.length)
            var span = StyleSpan(Typeface.BOLD);
            var builder = SpannableStringBuilder(text.toString())
            builder.setSpan(span, getText, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold
            setText(builder)*/

            //setText(remainingString + Html.fromHtml(sourceString))

            isCallWs = false
        }

        defaultMentionAdapter = SocialArrayAdapter<Mentionable>(context!!)
        defaultMentionAdapter!!.add(Mention(resources.getString(R.string.mention1_username)))
        defaultMentionAdapter!!.add(Mention(resources.getString(R.string.mention2_username)))
        defaultMentionAdapter!!.add(Mention(resources.getString(R.string.mention3_username)))
        setAdapter(defaultMentionAdapter)

    }
}