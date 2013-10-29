package com.switchfly.targetprocess.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.tasks.config.BaseRepositoryEditor;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.Consumer;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TargetProcessRepositoryEditor extends BaseRepositoryEditor<TargetProcessRepository> {

    private JBCheckBox myUseNTLM;
    private JTextField myHost;
    private JTextField myDomain;
    private JBLabel myHostLabel;
    private JBLabel myDomainLabel;

    public TargetProcessRepositoryEditor(Project project, TargetProcessRepository repository, Consumer<TargetProcessRepository> changeListener) {
        super(project, repository, changeListener);

        boolean useNTLM = repository.isUseNTLM();
        myUseNTLM.setSelected(useNTLM);

        myHost.setText(repository.getHost());
        myHost.setEnabled(useNTLM);

        myDomain.setText(repository.getDomain());
        myDomain.setEnabled(useNTLM);
    }

    @Nullable
    @Override
    protected JComponent createCustomPanel() {
        myUseNTLM = new JBCheckBox("Use NTLM Authentication");
        installListener(myUseNTLM);

        myHostLabel = new JBLabel("Host:", SwingConstants.RIGHT);
        myHost = new JTextField();
        myHost.setEnabled(false);
        installListener(myHost);

        myDomainLabel = new JBLabel("Domain:", SwingConstants.RIGHT);
        myDomain = new JTextField();
        myDomain.setEnabled(false);
        installListener(myDomain);

        return FormBuilder.createFormBuilder().addComponentToRightColumn(myUseNTLM, UIUtil.LARGE_VGAP).addLabeledComponent(myHostLabel, myHost)
                .addLabeledComponent(myDomainLabel, myDomain).getPanel();
    }

    @Override
    public void apply() {
        boolean ntlmEnabled = myUseNTLM.isSelected();
        myHost.setEnabled(ntlmEnabled);
        myDomain.setEnabled(ntlmEnabled);

        if (ntlmEnabled && myUseHttpAuthenticationCheckBox.isSelected()) {
            myUseHttpAuthenticationCheckBox.setSelected(false);
        }
        myUseHttpAuthenticationCheckBox.setEnabled(!ntlmEnabled);

        myRepository.setUseNTLM(ntlmEnabled);
        myRepository.setHost(myHost.getText().trim());
        myRepository.setDomain(myDomain.getText().trim());

        super.apply();
    }

    @Override
    public void setAnchor(@Nullable JComponent anchor) {
        super.setAnchor(anchor);
        myHostLabel.setAnchor(anchor);
        myDomainLabel.setAnchor(anchor);
    }
}
