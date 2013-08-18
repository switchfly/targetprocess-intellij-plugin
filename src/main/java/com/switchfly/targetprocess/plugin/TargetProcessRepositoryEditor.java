package com.switchfly.targetprocess.plugin;

public class TargetProcessRepositoryEditor// extends BaseRepositoryEditor<TargetProcessRepository>
 {
/*
    private final JCheckBox _myUseNTLM;
    private final JTextField _myHost;
    private final JTextField _myDomain;

    public TargetProcessRepositoryEditor(Project project, TargetProcessRepository repository, Consumer<TargetProcessRepository> changeListener) {
        super(project, repository, changeListener);

        _myUseNTLM = new JCheckBox();
        boolean enabled = repository.isUseNTLM();
        _myUseNTLM.setSelected(enabled);
        installListener(_myUseNTLM);
        myCustomPanel.add(_myUseNTLM, BorderLayout.NORTH);
        myCustomLabel.add(new JLabel("Use NTLM authentication:", SwingConstants.RIGHT) {
            @Override
            public Dimension getPreferredSize() {
                final Dimension oldSize = super.getPreferredSize();
                final Dimension size = _myUseNTLM.getPreferredSize();
                return new Dimension(oldSize.width, size.height);
            }
        }, BorderLayout.NORTH);

        _myHost = new JTextField();
        _myHost.setText(repository.getHost());
        _myHost.setEnabled(enabled);
        installListener(_myHost);
        myCustomPanel.add(_myHost, BorderLayout.CENTER);
        myCustomLabel.add(new JLabel("Host:", SwingConstants.RIGHT) {
            @Override
            public Dimension getPreferredSize() {
                final Dimension oldSize = super.getPreferredSize();
                final Dimension size = _myHost.getPreferredSize();
                return new Dimension(oldSize.width, size.height);
            }
        }, BorderLayout.CENTER);

        _myDomain = new JTextField();
        _myDomain.setText(repository.getDomain());
        _myDomain.setEnabled(enabled);
        installListener(_myDomain);
        myCustomPanel.add(_myDomain, BorderLayout.SOUTH);
        myCustomLabel.add(new JLabel("Domain:", SwingConstants.RIGHT) {
            @Override
            public Dimension getPreferredSize() {
                final Dimension oldSize = super.getPreferredSize();
                final Dimension size = _myDomain.getPreferredSize();
                return new Dimension(oldSize.width, size.height);
            }
        }, BorderLayout.SOUTH);
    }

    @Override
    public void apply() {
        super.apply();
        boolean ntlmEnabled = _myUseNTLM.isSelected();
        _myHost.setEnabled(ntlmEnabled);
        _myDomain.setEnabled(ntlmEnabled);
        myUseHTTPAuthentication.setEnabled(!ntlmEnabled);

        if (ntlmEnabled && myUseHTTPAuthentication.isSelected()) {
            myUseHTTPAuthentication.setSelected(false);
        }

        myRepository.setUseNTLM(ntlmEnabled);
        myRepository.setHost(_myHost.getText().trim());
        myRepository.setDomain(_myDomain.getText().trim());
    }*/
}
