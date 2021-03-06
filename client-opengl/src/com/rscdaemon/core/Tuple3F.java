package com.rscdaemon.core;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.FloatBuffer;
import java.util.IllegalFormatException;

import com.rscdaemon.internal.StringFormatter;

// TODO: Finish
public class Tuple3F
	extends
		Tuple2F
{

	private static final long serialVersionUID = -7370373436360126681L;
	
	/// Default format example: "(x, y, z)"
	private final static StringFormatter FORMATTER =
			new StringFormatter(Tuple3F.class, "(%s0, %s1, %s2)");

	// Quickly fail if the formatter override is invalid
	static
	{
		try
		{
			FORMATTER.format(0, 0, 0);
		}
		catch(IllegalFormatException ife)
		{
			System.err.println("An illegal string format has been provided "
					+ "for class " + Tuple3F.class.getName() + " .");
		}
	}
	
	private float z;

	/**
	 * {@inheritDoc}
	 * 
	 * Returns a string defined by the formatter statically bound to this class
	 * 
	 * @since 1.0
	 * 
	 */
	@Override
	public String toString()
	{
		return FORMATTER.format(super.getX(), super.getY(), z);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @since 1.0
	 * 
	 */
	@Override
	public int hashCode()
	{
		long rv = super.hashCode();
		// Float.floatToIntBits returns different values for -0.0 and +0.0
		// So intercept both those cases to meet the method contract
		rv += z == 0 ? 0 : Float.floatToIntBits(z);
		rv *= 11;
		return (int) (rv);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @since 1.0
	 * 
	 */
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof Tuple3F))
		{
			return false;
		}
		Tuple3F rhs = (Tuple3F)o;
		return super.equals(rhs) && rhs.z == z;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @since 1.0
	 * 
	 */
	@Override
	public boolean equalsWithEpsilon(EpsilonEquals other, float epsilon)
	{
		if(epsilon < 0)
		{
			throw new IllegalArgumentException("Epsilon must not be negative");
		}
		if(!(other instanceof Tuple3F))
		{
			return false;
		}
		Tuple3F rhs = (Tuple3F)other;
		return Math.abs(super.getX() - rhs.getX()) <= epsilon && 
			   Math.abs(super.getY() - rhs.getY()) <= epsilon && 
			   Math.abs(z - rhs.z) <= epsilon;
	}
	
	/**
	 * Constructs a <code>Tuple3F</code> with both elements set to 0
	 * 
	 * @since 1.0
	 * 
	 */
	public Tuple3F()
	{
		super();
		// "Generally" most JREs default instance ints to '0'...
		// but it's not in the JRE specification, so do it explicitly
		this.z = 0;
	}
	
	/**
	 * Constructs a <code>Tuple3F</code> from the provided values
	 * 
	 * @param x the first element of this tuple
	 * 
	 * @param y the second element of this tuple
	 * 
	 * @param z the third element of this tuple
	 * 
	 * @since 1.0
	 * 
	 */
	public Tuple3F(float x, float y, float z)
	{
		super(x, y);
		this.z = z;
	}
	
	/**
	 * Constructs a <code>Tuple3F</code> from the provided array, starting 
	 * at the provided offset
	 * 
	 * @param buffer the array to extract values from
	 * 
	 * @param offset the offset to begin extracting at
	 * 
	 * @throws IndexOutOfBoundsException if the offset is less than 3 
	 * elements from the end of the provided array
	 * 
	 * @throws NullPointerException if the provided array is null
	 * 
	 * @since 1.0
	 * 
	 */
	public Tuple3F(float[] buffer, int offset)
	{
		super(buffer, offset);
		this.z = buffer[offset + 2];
	}
	
	/**
	 * Constructs a <code>Tuple3F</code> from the provided array, starting 
	 * at index 0
	 * 
	 * @param buffer the array to extract values from
	 * 
	 * @throws IndexOutOfBoundsException if the provided array has a length of 
	 * less than 3
	 * 
	 * @throws NullPointerException if the provided array is null
	 * 
	 * @since 1.0
	 * 
	 */
	public Tuple3F(float[] buffer)
	{
		this(buffer, 0);
	}

	/**
	 * Constructs a <code>Tuple3F</code> from the provided {@link FloatBuffer}
	 * 
	 * @param buffer the {@link FloatBuffer} to extract values from
	 * 
	 * @throws BufferUnderflowException if there are fewer than 3 elements in 
	 * the provided buffer
	 * 
	 * @throws NullPointerException if the provided buffer is null
	 * 
	 * @since 1.0
	 * 
	 */
	public Tuple3F(FloatBuffer buffer)
	{
		super(buffer);
		this.z = buffer.get();
	}
	
	/**
	 * Constructs a <code>Tuple3F</code> from the provided <code>Tuple3F</code>
	 * 
	 * @param other the other tuple to extract values from
	 * 
	 * @throws NullPointerException if the provided tuple is null
	 * 
	 * @since 1.0
	 * 
	 */
	public Tuple3F(Tuple3F other)
	{
		super(other);
		this.z = other.z;
	}
	
	/**
	 * Retrieves the third element of this tuple
	 * 
	 * @return the third element of this tuple
	 * 
	 * @since 1.0
	 * 
	 */
	public final float getZ()
	{
		return z;
	}
	
	/**
	 * Inserts the elements of this tuple into the provided array at the 
	 * provided offset
	 * 
	 * @param buffer the array to insert the elements of this tuple into
	 * 
	 * @param offset the offset to start inserting at
	 * 
	 * @throws IndexOutOfBoundsException if offset is fewer than 3 elements 
	 * from the end of the provided array
	 * 
	 * @throws NullPointerException if the provided array is null
	 * 
	 * @since 1.0
	 * 
	 */
	@Override
	public void set(float[] buffer, int offset)
	{
		super.set(buffer, offset);
		buffer[offset + 2] = z;
	}
	
	/**
	 * Inserts the elements of this tuple into the provided array at offset 0
	 * 
	 * @param buffer the array to insert the elements of this tuple into
	 * 
	 * @throws IndexOutOfBoundsException if the provided array has a length of 
	 * less than 3
	 * 
	 * @throws NullPointerException if the provided array is null
	 * 
	 * @since 1.0
	 * 
	 */
	@Override
	public void set(float[] buffer)
	{
		set(buffer, 0);
	}
	
	/**
	 * Inserts the elements of this tuple into the provided {@link FloatBuffer}
	 * 
	 * @param buffer the {@link FloatBuffer} to insert values into
	 * 
	 * @throws BufferOverflowException if there is not enough room in the 
	 * {@link FloatBuffer}
	 * 
	 * @throws NullPointerException if the provided {@link FloatBuffer} is null
	 * 
	 * @since 1.0
	 * 
	 */
	@Override
	public void set(FloatBuffer buffer)
	{
		super.set(buffer);
		buffer.put(z);
	}
	
	/**
	 * Sets the third element of this tuple
	 * 
	 * @param z the new value to set
	 * 
	 * @since 1.0
	 * 
	 */
	public final void setZ(float z)
	{
		this.z = z;
	}
	
	/**
	 * Sets the elements of this tuple
	 * 
	 * @param x the new value of the first element
	 * 
	 * @param y the new value of the second element
	 * 
	 * @param z the new value of the third element
	 * 
	 * @since 1.0
	 * 
	 */
	public final void set(float x, float y, float z)
	{
		super.set(x, y);
		this.z = z;
	}
	
	/**
	 * Sets the elements of this tuple from the provided array at the provided 
	 * offset
	 * 
	 * @param buffer the array to extract values from
	 * 
	 * @param offset the offset to start extracting values at
	 * 
	 * @throws NullPointerException if the provided array is null
	 * 
	 * @throws IndexOutOfBoundsException if the provided offset is fewer than 
	 * 3 elements from the end of the provided array
	 * 
	 * @since 1.0
	 * 
	 */
	@Override
	public void get(float[] buffer, int offset)
	{
		super.get(buffer, offset);
		this.z = buffer[offset + 2];
	}
	
	/**
	 * Sets the elements of this tuple from the provided array at offset 0
	 * 
	 * @param buffer the array to extract values from
	 * 
	 * @throws NullPointerException if the provided array is null
	 * 
	 * @throws IndexOutOfBoundsException if the provided array has a length of 
	 * less than 3
	 * 
	 * @since 1.0
	 * 
	 */
	@Override
	public void get(float[] buffer)
	{
		get(buffer, 0);
	}
	
	/**
	 * Sets the elements of this tuple from the provided {@link FloatBuffer}
	 * 
	 * @param buffer the {@link FloatBuffer} to extract values from
	 * 
	 * @throws BufferUnderflowException if there are fewer than 3 values 
	 * remaining in the buffer
	 * 
	 * @throws NullPointerException if the provided {@link FloatBuffer} is null
	 * 
	 * @since 1.0
	 * 
	 */
	@Override
	public void get(FloatBuffer buffer)
	{
		super.get(buffer);
		this.z = buffer.get();
	}
	
	/**
	 * Performs an element-wise linear scaling on this tuple
	 * 
	 * @param factor the factor by which to scale this tuple
	 * 
	 * @since 1.0
	 * 
	 */
	@Override
	public void scale(float factor)
	{
		super.scale(factor);
		this.z *= factor;
	}
	
	/**
	 * Performs an element-wise negation on this tuple.
	 * 
	 * @since 1.0
	 * 
	 */
	@Override
	public void negate()
	{
		super.negate();
		this.z = -this.z;
	}
	
	/**
	 * Adds the provided tuple to this one
	 * 
	 * @param other the tuple to add
	 * 
	 * @throws NullPointerException if the provided tuple is null
	 * 
	 * @since 1.0
	 */
	public final void add(Tuple3F other)
	{
		super.add(other);
		this.z += other.z;
	}
	
	/**
	 * Subtracts the provided tuple from this one
	 * 
	 * @param other the tuple to subtract
	 * 
	 * @throws NullPointerException if the provided tuple is null
	 * 
	 * @since 1.0
	 * 
	 */
	public final void subtract(Tuple3F other)
	{
		super.subtract(other);
		this.z -= other.z;
	}
	
	/**
	 * Clamps each element of this tuple to the provided range
	 * 
	 * @param minimum the minimum (inclusive) value to clamp to
	 * 
	 * @param maximum the maximum (inclusive) value to clamp to
	 * 
	 * @since 1.0
	 * 
	 */
	@Override
	public void clamp(float minimum, float maximum)
	{
		super.clamp(minimum, maximum);
		
		if(z < minimum)
		{
			z = minimum;
		}
		else if(z > maximum)
		{
			z = maximum;
		}
	}
}
