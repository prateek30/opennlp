///////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2000 Jason Baldridge
// 
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
// 
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////

package opennlp.common.hylo;

import opennlp.common.synsem.*;
import opennlp.common.unify.*;
import org.jdom.*;
import java.util.*;

public class Op extends HyloFormula {
    protected final String _name;
    protected List _args;
    
    public Op (Element e) {
	_name = e.getAttributeValue("n");
	List argElements = e.getChildren();
	int argSize = argElements.size();
	_args = new ArrayList(argSize);
	for (int i=0; i<argSize; i++) {
	    _args.add(HyloHelper.getLF((Element)argElements.get(i)));
	}
    }

    public Op (String name, List args) {
	_name = name;
	_args = args;
    }
    
    public void addArgument (LF formula) {
	_args.add(formula);
    }

    public boolean occurs (Variable var) {
	for (Iterator argsIt = _args.iterator(); argsIt.hasNext(); ) {
	    if (((LF)argsIt.next()).occurs(var)) {
		return true;
	    }
	}
	return false;
    }

    public boolean equals (Object o) {
	if (o instanceof Op && _name == ((Op)o)._name) {
	    List oArgs = ((Op)o)._args;
	    if (_args.size() == oArgs.size()) {
		for (Iterator argsIt = _args.iterator(); argsIt.hasNext();) {
		    boolean found = false;
		    Object arg = argsIt.next();
		    for (Iterator oArgsIt = oArgs.iterator();
			 !found && oArgsIt.hasNext(); ) {
			if (arg.equals(oArgsIt.next())) {
			    found = true;
			}
		    }
		    if (!found) {
			return false;
		    }
		}
		return true;
	    } else {
		return false;
	    }
	} else {
	    return false;
	}
    }
    
    public Object unifyCheck (Object o) throws UnifyFailure {
	if (o instanceof Op && _name.equals(((Op)o)._name)) {
	    return this;
	} else {
	    throw new UnifyFailure();
	}
    }

    public Object fill (Substitution sub) {
	List $args = new ArrayList();
	for (Iterator argsIt = _args.iterator(); argsIt.hasNext();) {
	    LF arg = (LF)argsIt.next();
	    if (arg instanceof Variable) {
		LF $arg = (LF)sub.getValue((Variable)arg);
		if ($arg != null) {
		    $args.add($arg);
		} else {
		    $args.add(arg);
		}
	    } else {
		$args.add(arg);
	    }
	}
	return new Op(_name, $args);
    }
    
    public String toString () {
	StringBuffer sb = new StringBuffer();
	String opString = printOp(_name);
	if (_args.size() == 1) {
	    sb.append(opString);
	    sb.append(_args.get(0).toString());
	} else {
	    Iterator argsIt = _args.iterator();
	    sb.append(argsIt.next().toString());
	    for (; argsIt.hasNext(); ) {
		sb.append(' ').append(opString).append(' ');
		sb.append(argsIt.next().toString());
	    }
	}
	return sb.toString();
    }
    
    public static String printOp (String o) {
	if (o.equals("conj"))       return "^";
	else if (o.equals("disj"))  return "v";
	else if (o.equals("neg"))   return "~";
	else                        return o;
    }

}
