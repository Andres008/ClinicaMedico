/**
 *  Copyright 2009-2020 PrimeTek.
 *
 *  Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.primefaces.california.view.misc.terminal;

import javax.faces.view.ViewScoped;

import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

@Named("terminalBasicView")
@ViewScoped
public class BasicView implements Serializable {
    
    public String handleCommand(String command, String[] params) {
		if(command.equals("greet")) {
            if(params.length > 0)
                return "Hello " + params[0];
            else
                return "Hello Stranger";
        }
        else if(command.equals("date"))
            return new Date().toString();
        else
            return command + " not found";
        }
}
