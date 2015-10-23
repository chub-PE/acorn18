package ui.helper;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DoubleSpinner extends JSpinner
{

    private static final long serialVersionUID = 1L;
    private static final double STEP_RATIO = 0.5;

    private SpinnerNumberModel model;

    public DoubleSpinner()
    {
        super();
        model = new SpinnerNumberModel(0.0, -1000.0, 1000.0, STEP_RATIO);
        this.setModel(model);

        this.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                Double value = getDouble();
            }
        });
    }

    /**
     * Returns the current value as a Double
     */
    public Double getDouble()
    {
    	return (Double)getValue();
    }
}
