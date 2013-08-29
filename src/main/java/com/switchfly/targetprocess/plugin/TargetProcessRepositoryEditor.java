package com.switchfly.targetprocess.plugin;

import javax.swing.*;
import com.intellij.openapi.project.Project;
import com.intellij.tasks.config.BaseRepositoryEditor;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.Consumer;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.Nullable;

public class TargetProcessRepositoryEditor extends BaseRepositoryEditor<TargetProcessRepository> {

    private JBCheckBox _myUseNTLM;
    private JTextField _myHost;
    private JTextField _myDomain;
    private JBLabel _myHostLabel;
    private JBLabel _myDomainLabel;

    public TargetProcessRepositoryEditor(Project project, TargetProcessRepository repository, Consumer<TargetProcessRepository> changeListener) {
        super(project, repository, changeListener);

        boolean useNTLM = repository.isUseNTLM();
        _myUseNTLM.setSelected(useNTLM);

        _myHost.setText(repository.getHost());
        _myHost.setEnabled(useNTLM);

        _myDomain.setText(repository.getDomain());
        _myDomain.setEnabled(useNTLM);
    }

    @Nullable
    @Override
    protected JComponent createCustomPanel() {
        _myUseNTLM = new JBCheckBox("Use NTLM Authentication");
        installListener(_myUseNTLM);

        _myHostLabel = new JBLabel("Host:", SwingConstants.RIGHT);
        _myHost = new JTextField();
        _myHost.setEnabled(false);
        installListener(_myHost);

        _myDomainLabel = new JBLabel("Domain:", SwingConstants.RIGHT);
        _myDomain = new JTextField();
        _myDomain.setEnabled(false);
        installListener(_myDomain);

        return FormBuilder.createFormBuilder().addComponentToRightColumn(_myUseNTLM, UIUtil.LARGE_VGAP).addLabeledComponent(_myHostLabel, _myHost)
            .addLabeledComponent(_myDomainLabel, _myDomain).getPanel();
    }

    @Override
    public void apply() {
        boolean ntlmEnabled = _myUseNTLM.isSelected();
        _myHost.setEnabled(ntlmEnabled);
        _myDomain.setEnabled(ntlmEnabled);

        if (ntlmEnabled && myUseHttpAuthenticationCheckBox.isSelected()) {
            myUseHttpAuthenticationCheckBox.setSelected(false);
        }
        myUseHttpAuthenticationCheckBox.setEnabled(!ntlmEnabled);

        myRepository.setUseNTLM(ntlmEnabled);
        myRepository.setHost(_myHost.getText().trim());
        myRepository.setDomain(_myDomain.getText().trim());

        super.apply();
    }

    @Override
    public void setAnchor(@Nullable JComponent anchor) {
        super.setAnchor(anchor);
        _myHostLabel.setAnchor(anchor);
        _myDomainLabel.setAnchor(anchor);
    }
}
