package ma.org.comfybackend.security.Enumerations;

import java.io.Serializable;

public enum CommandState implements Serializable {
    placed,
    confirmed,

    inDelivery,

    cancelled,

    recieved,


}
