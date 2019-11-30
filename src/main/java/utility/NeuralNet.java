package utility;

import java.io.Serializable;

public enum NeuralNet implements Serializable {

    // VGG16 and VGG19 causes SegFault at Azure server - too large
    /*VGG16, VGG19, */MobileNet, ResNet, DenseNet;

    private static final long serialVersionUID = 229L;

}
