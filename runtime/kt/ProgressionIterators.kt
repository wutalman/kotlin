package jet

import jet.runtime.ProgressionUtil

class ByteProgressionIterator(start: Byte, end: Byte, val increment: Int) : ByteIterator() {
    private var next = start.toInt()
    private val finalElement = ProgressionUtil.getProgressionFinalElement(start.toInt(), end.toInt(), increment).toByte()
    private var hasNext = if (increment > 0) start <= end else start >= end

    override fun hasNext() = hasNext

    override fun nextByte(): Byte {
        val value = next
        if (value == finalElement.toInt()) {
            hasNext = false
        }
        else {
            next += increment
        }
        return value.toByte()
    }
}

class CharProgressionIterator(start: Char, end: Char, val increment: Int) : CharIterator() {
    private var next = start.toInt()
    private val finalElement = ProgressionUtil.getProgressionFinalElement(start.toInt(), end.toInt(), increment).toChar()
    private var hasNext = if (increment > 0) start <= end else start >= end

    override fun hasNext() = hasNext

    override fun nextChar(): Char {
        val value = next
        if (value == finalElement.toInt()) {
            hasNext = false
        }
        else {
            next += increment
        }
        return value.toChar()
    }
}

class ShortProgressionIterator(start: Short, end: Short, val increment: Int) : ShortIterator() {
    private var next = start.toInt()
    private val finalElement = ProgressionUtil.getProgressionFinalElement(start.toInt(), end.toInt(), increment).toShort()
    private var hasNext = if (increment > 0) start <= end else start >= end

    override fun hasNext() = hasNext

    override fun nextShort(): Short {
        val value = next
        if (value == finalElement.toInt()) {
            hasNext = false
        }
        else {
            next += increment
        }
        return value.toShort()
    }
}

class IntProgressionIterator(start: Int, end: Int, val increment: Int) : IntIterator() {
    private var next = start
    private val finalElement = ProgressionUtil.getProgressionFinalElement(start, end, increment)
    private var hasNext = if (increment > 0) start <= end else start >= end

    override fun hasNext() = hasNext

    override fun nextInt(): Int {
        val value = next
        if (value == finalElement) {
            hasNext = false
        }
        else {
            next += increment
        }
        return value
    }
}

class LongProgressionIterator(start: Long, end: Long, val increment: Long) : LongIterator() {
    private var next = start
    private val finalElement = ProgressionUtil.getProgressionFinalElement(start, end, increment)
    private var hasNext = if (increment > 0) start <= end else start >= end

    override fun hasNext() = hasNext

    override fun nextLong(): Long {
        val value = next
        if (value == finalElement) {
            hasNext = false
        }
        else {
            next += increment
        }
        return value
    }
}

class FloatProgressionIterator(start: Float, val end: Float, val increment: Float) : FloatIterator() {
    private var next = start
    
    override fun hasNext() = if (increment > 0) next <= end else next >= end
    
    override fun nextFloat(): Float {
        val value = next
        next += increment
        return value
    }
}

class DoubleProgressionIterator(start: Double, val end: Double, val increment: Double) : DoubleIterator() {
    private var next = start
    
    override fun hasNext() = if (increment > 0) next <= end else next >= end
    
    override fun nextDouble(): Double {
        val value = next
        next += increment
        return value
    }
}
