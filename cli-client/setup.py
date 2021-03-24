from setuptools import setup, find_packages

setup(
    name="ev_group41",
    version="0.0.1",
    author="Mika_Shady",
    description="A CLI for SoftEng20 Project.",
    url="https://github.com/ntua/TL20-41/cli-client",
    packages = find_packages(),
    install_requires=['click', 'requests','datetime', 'click_option_group'],
    entry_points={
        'console_scripts': [
            'ev_group41 =ev_group41:main'
        ]
    }
)
