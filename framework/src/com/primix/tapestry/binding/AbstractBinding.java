package com.primix.tapestry.binding;

import com.primix.tapestry.*;

/*
 * Tapestry Web Application Framework
 * Copyright (c) 2000 by Howard Ship and Primix Solutions
 *
 * Primix Solutions
 * One Arsenal Marketplace
 * Watertown, MA 02472
 * http://www.primix.com
 * mailto:hship@primix.com
 * 
 * This library is free software.
 * 
 * You may redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation.
 *
 * Version 2.1 of the license should be included with this distribution in
 * the file LICENSE, as well as License.html. If the license is not
 * included with this distribution, you may find a copy at the FSF web
 * site at 'www.gnu.org' or 'www.fsf.org', or you may write to the
 * Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139 USA.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 */

/**
 *  Base class for {@link IBinding} implementations.
 *
 * @author Howard Ship
 * @version $Id$
 */


public abstract class AbstractBinding implements IBinding
{
	public boolean getBoolean()
	{
		Boolean booleanValue;
		Object value;
		Number numberValue;
		char[] data;

		value = getValue();

		if (value == null)
			return false;

		try
		{
			booleanValue = (Boolean)value;
			return booleanValue.booleanValue();
		}
		catch (ClassCastException e)
		{
			// Not Boolean, maybe Number
		}

		try
		{
			numberValue = (Number) value;
			return (numberValue.intValue() != 0);
		}
		catch (ClassCastException e)
		{
			// Not Number, maybe String
		}

		try
		{
			int i;
			char ch;

				data = ((String)value).toCharArray();

			for (i = 0;; i++)
			{
				if (!Character.isWhitespace(data[i]))
					return true;
			}
		}
		catch (IndexOutOfBoundsException e)
		{
			// Hit end-of-string before finding a non-whitespace character

			return false;
		}
		catch (ClassCastException e)
		{
			// Not a String.
		}

		// The value is true because it is not null.

		return true;
	}

	public int getInt() throws NullValueForBindingException
	{
		Object raw;

		raw = getValue();
		if (raw == null)
			throw new NullValueForBindingException(this);

		try
		{
			return ((Number)raw).intValue();
		}
		catch (ClassCastException e)
		{
		}

		try
		{
			return ((Boolean)raw).booleanValue() 
			? 1 
				: 0;
		}
		catch (ClassCastException e)
		{
		}

		// Save parsing for last.  This may also throw a number format exception.

		return Integer.parseInt((String)raw);
	}

	public Integer getInteger()
	{
		return (Integer)getValue();
	}

	/**
	*  Gets the value for the binding.  If null, returns null,
	*  otherwise, returns the String (<code>toString()</code>) version of
	*  the value.
	*
	*/

	public String getString()
	{
		Object value;

		value = getValue();
		if (value == null)
			return null;

		return value.toString();
	}

	/**
	*  Default implementation: returns false.
	*
	*/

	public boolean isStatic()
	{
		return false;
	}
}

