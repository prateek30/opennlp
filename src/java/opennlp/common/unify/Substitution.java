///////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2000 Jason Baldridge and Gann Bierner
// 
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
// 
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////
package opennlp.common.unify;

/**
 * Specifies how variable are to be replaced to make two objects unify.  
 *
 * @author      Gann Bierner & Jason Baldridge
 * @version     $Revision: 1.2 $, $Date: 2001/11/22 15:04:50 $
 */
public interface Substitution {
    public boolean makeSubstitution(Variable var, Object o);
    public Object getValue(Variable var);
    public Object fill(Object o);
}
