package com.app.dubaiculture.ui.components.customreadmore

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.components.customtextview.FontStyle

class CustomReadMoreView(context: Context, attrs: AttributeSet?) :
    AppCompatTextView(context, attrs) {
    private var mText: CharSequence? = null
    private val TRIM_MODE_LINES = 0
    private val TRIM_MODE_LENGTH = 1
    private val DEFAULT_TRIM_LENGTH = 240
    private val DEFAULT_TRIM_LINES = 2
    private val INVALID_END_INDEX = -1
    private val DEFAULT_SHOW_TRIM_EXPANDED_TEXT = true
    private val ELLIPSIZE = "... "
    private var readMore = true

    private var bufferType: BufferType? = null
    private var trimLength = 0
    private var trimCollapsedText: CharSequence? = null
    private var trimExpandedText: CharSequence? = null
    private var viewMoreSpan: ReadMoreClickableSpan? = null
    private var colorClickableText = 0
    private var showTrimExpandedText = false

    private var trimMode = 0
    private var lineEndIndex = 0
    private var trimLines = 0

    private val defaultFontStyle = FontStyle.REGULAR
    private var readMoreClickListener: ReadMoreClickListener? = null

    init {
        var selectedFontStyle = defaultFontStyle

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomReadMoreView)
        trimLength =
            typedArray.getInt(R.styleable.CustomReadMoreView_trimLength, DEFAULT_TRIM_LENGTH)
        selectedFontStyle =
            FontStyle.fromId(
                typedArray.getInt(
                    R.styleable.CustomReadMoreView_customReadMoreTextFont,
                    defaultFontStyle.id
                )
            )
        val resourceIdTrimCollapsedText = typedArray.getResourceId(
            R.styleable.CustomReadMoreView_trimCollapsedText,
            R.string.read_more
        )
        val resourceIdTrimExpandedText = typedArray.getResourceId(
            R.styleable.CustomReadMoreView_trimExpandedText,
            R.string.read_less
        )
        trimCollapsedText = resources.getString(resourceIdTrimCollapsedText)
        trimExpandedText = resources.getString(resourceIdTrimExpandedText)
        trimLines =
            typedArray.getInt(R.styleable.CustomReadMoreView_trimLines, DEFAULT_TRIM_LINES)
        colorClickableText = typedArray.getColor(
            R.styleable.CustomReadMoreView_colorClickableText,
            ContextCompat.getColor(context, R.color.colorPrimary)
        )
        showTrimExpandedText = typedArray.getBoolean(
            R.styleable.CustomReadMoreView_showTrimExpandedText,
            DEFAULT_SHOW_TRIM_EXPANDED_TEXT
        )
        trimMode = typedArray.getInt(R.styleable.CustomReadMoreView_trimMode, TRIM_MODE_LINES)
        typedArray.recycle()

        setupFont(selectedFontStyle)
        viewMoreSpan = ReadMoreClickableSpan()
        onGlobalLayoutLineEndIndex()
        setText()
    }

    fun initialize(text: CharSequence?, readMoreListener: ReadMoreClickListener? = null) {
        readMoreClickListener = readMoreListener
        setText(text)
    }

    fun renderFromState(rm: Boolean) {
        readMore = rm
        setText()
    }

    private fun setText() {
        super.setText(getDisplayableText(), bufferType)
        movementMethod = LinkMovementMethod.getInstance()
        highlightColor = Color.TRANSPARENT
    }

    private fun getDisplayableText(): CharSequence? {
        return getTrimmedText(mText)
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        mText = text
        bufferType = type
        setText()
    }

    private fun getTrimmedText(text: CharSequence?): CharSequence? {
        if (trimMode == TRIM_MODE_LENGTH) {
            if (text != null && text.length > trimLength) {
                return if (readMore) {
                    updateCollapsedText()
                } else {
                    updateExpandedText()
                }
            }
        }
        if (trimMode == TRIM_MODE_LINES) {
            if (text != null && lineEndIndex > 0) {
                if (readMore) {
                    if (layout.lineCount > trimLines) {
                        return updateCollapsedText()
                    }
                } else {
                    return updateExpandedText()
                }
            }
        }
        return text
    }

    private fun updateCollapsedText(): CharSequence? {
            var trimEndIndex = mText!!.length
            when (trimMode) {
                TRIM_MODE_LINES -> {
                    trimEndIndex =
                        lineEndIndex - (ELLIPSIZE.length + trimCollapsedText!!.length + 1)
                    if (trimEndIndex < 0) {
                        trimEndIndex = trimLength + 1
                    }
                }
                TRIM_MODE_LENGTH -> trimEndIndex = trimLength + 1
            }
            val s = SpannableStringBuilder(mText, 0, trimEndIndex)
                .append(ELLIPSIZE)
                .append(trimCollapsedText)
        val ss = SpannableString.valueOf(s.toString())
        val image = ContextCompat.getDrawable(context, R.drawable.arrow_readmore_)
        image!!.setBounds(0, 0, image.intrinsicWidth, image.intrinsicHeight)
        val imageSpan = ImageSpan(image, ImageSpan.ALIGN_BOTTOM)
        ss.setSpan(
            imageSpan,
            s.length-1 ,
            s.length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
            return addClickableSpan(ss, trimCollapsedText)



    }

    private fun updateExpandedText(): CharSequence? {
        if (showTrimExpandedText) {
            val s = SpannableStringBuilder(mText, 0, mText!!.length).append(trimExpandedText)
            val ss = SpannableString.valueOf(s.toString())
            val image = ContextCompat.getDrawable(context, R.drawable.arrow_readless)
            image!!.setBounds(0, 0, image.intrinsicWidth, image.intrinsicHeight)
            val imageSpan = ImageSpan(image, ImageSpan.ALIGN_BOTTOM)
            ss.setSpan(
                imageSpan,
                s.length-1,
                s.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
            return addClickableSpan(ss, trimExpandedText)
        }
        return mText
    }

    private fun addClickableSpan(
        s: SpannableString,
        trimText: CharSequence?
    ): CharSequence? {
        s.setSpan(
            viewMoreSpan,
            s.length - trimText!!.length,
            s.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return s
    }

    fun setTrimLength(trimLength: Int) {
        this.trimLength = trimLength
        setText()
    }

    fun setColorClickableText(colorClickableText: Int) {
        this.colorClickableText = colorClickableText
    }

    fun setTrimCollapsedText(trimCollapsedText: CharSequence?) {
        this.trimCollapsedText = trimCollapsedText
    }

    fun setTrimExpandedText(trimExpandedText: CharSequence?) {
        this.trimExpandedText = trimExpandedText
    }

    fun setTrimMode(trimMode: Int) {
        this.trimMode = trimMode
    }

    fun setTrimLines(trimLines: Int) {
        this.trimLines = trimLines
    }

    inner class ReadMoreClickableSpan : ClickableSpan() {

        override fun onClick(widget: View) {

            readMore = !readMore
            readMoreClickListener?.onClick(readMore)
            setText()
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = colorClickableText

        }
    }

    private fun onGlobalLayoutLineEndIndex() {
        if (trimMode == TRIM_MODE_LINES) {
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val obs = viewTreeObserver
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        obs.removeOnGlobalLayoutListener(this)
                    } else {
                        obs.removeGlobalOnLayoutListener(this)
                    }
                    refreshLineEndIndex()
                    setText()
                }
            })
        }
    }

    private fun refreshLineEndIndex() {
        try {
            lineEndIndex = if (trimLines == 0) {
                layout.getLineEnd(0)
            } else if (trimLines > 0 && lineCount >= trimLines) {
                layout.getLineEnd(trimLines - 1)
            } else {
                INVALID_END_INDEX
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupFont(selectedFontStyle: FontStyle) {
        val typeface = when (selectedFontStyle) {
            FontStyle.LIGHT ->
                ResourcesCompat.getFont(context, R.font.neosansarabiclight)
            FontStyle.REGULAR ->
                ResourcesCompat.getFont(context, R.font.neo_sans_arabic_regular)
            FontStyle.BOLD ->
                ResourcesCompat.getFont(context, R.font.neosansarabicbold)
        }
        setTypeface(typeface)
    }
    private fun drawImg(){
        val image = ContextCompat.getDrawable(context, R.drawable.checkbox_tick_mark)
        image!!.setBounds(0, 0, image.intrinsicWidth, image.intrinsicHeight)
// Replace blank spaces with image icon
// Replace blank spaces with image icon
        val myText = "myText"
        val textLength = myText.length
        val sb = SpannableString("$myText   This is another text")
        val imageSpan = ImageSpan(image, ImageSpan.ALIGN_BOTTOM)
        sb.setSpan(imageSpan, textLength, textLength + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    }
}